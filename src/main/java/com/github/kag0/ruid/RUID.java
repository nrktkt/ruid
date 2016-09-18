package com.github.kag0.ruid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import static java.util.Objects.requireNonNull;

/**
 * A Random Unique/Universal ID.
 * RUIDs are uniformly distributed, are 24 ascii characters when encoded,
 * and are unique by 18 bytes of entropy.
 * Created by nfischer on 9/17/2016.
 */
@JsonSerialize(using = ToStringSerializer.class)
public class RUID implements Comparable<RUID>, Serializable{
	public static final int BINARY_SIZE = 18;
	private static final Random RANDOM = new SecureRandom();

	private byte[] bytes;
	private String encoded;

	/**
	 * Create a RUID from source bytes
	 * @param bytes 18 random bytes
	 */
	public RUID(byte[] bytes){
		setBytes(bytes);
	}

	public RUID(String encoded){
		byte[] bytes = Base64.getUrlDecoder()
				.decode(requireNonNull(encoded, "Compact RUID cannot be null"));
		this.encoded = encoded;
		setBytes(bytes);
	}

	private RUID(){}

	/**
	 * Parse an RUID from a string.
	 * @param compact
	 * @throws IllegalArgumentException
	 */
	public static RUID parse(String compact){
		return new RUID(compact);
	}

	public static RUID generate(){
		byte[] bs = new byte[BINARY_SIZE];
		RANDOM.nextBytes(bs);
		RUID ruid = new RUID();
		ruid.bytes = bs;
		return ruid;
	}


	private void setBytes(byte... bytes){
		requireNonNull(bytes, "RUID bytes cannot be null.");
		if(bytes.length != BINARY_SIZE)
			throw new IllegalArgumentException(
					String.format("RUID must be %d bytes.", BINARY_SIZE));
		this.bytes = Arrays.copyOf(bytes, BINARY_SIZE);
	}

	/**
	 *
	 * @return the binary representation of the RUID
	 */
	public byte[] bytes(){
		return Arrays.copyOf(bytes, BINARY_SIZE);
	}

	/**
	 * Returns the internal bytes of the RUID. Use this only when you need to
	 * avoid memory allocation and know that the array will not be modified
	 * @return the backing byte array of the RUID.
	 */
	public byte[] rawBytes(){
		return bytes;
	}

	/**
	 *
	 * @return this RUID encoded as a encoded string
	 */
	public String encoded(){
		if(encoded != null)
			return encoded;
		encoded = Base64.getUrlEncoder().encodeToString(bytes);
		return encoded;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RUID)) return false;

		RUID ruid = (RUID) o;

		return Arrays.equals(bytes, ruid.bytes);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(bytes);
	}

	@Override
	public String toString(){
		return encoded();
	}

	@Override
	public int compareTo(RUID o) {
		if(!(
				this == o
				|| this.bytes == o.bytes
				|| (this.encoded != null
					&& this.encoded == o.encoded)
		)) {
			for (int i = 0; i < BINARY_SIZE; i++) {
				int res = this.bytes[i] - o.bytes[i];
				if (res != 0)
					return res;
			}
		}
		return 0;
	}
}
