package com.github.kag0.ruid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/17/2016.
 */
public class RUIDTest {
	@Test
	public void parse() {
		RUID gen = RUID.generate();
		System.out.println(gen);
		assertEquals(gen, RUID.parse(gen.toString()));
		assertEquals(gen.hashCode(), new RUID(gen.bytes()).hashCode());

		List<RUID> sorted = Stream.generate(RUID::generate)
				.limit(30)
				.sorted()
				.collect(toList());

		List<RUID> shuffled = new ArrayList<>(sorted);
		Collections.shuffle(shuffled);
		Collections.sort(shuffled);

		assertEquals(sorted, shuffled);
	}

	@Test
	public void json() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		RUID original = RUID.generate();
		String json = mapper.writeValueAsString(original);
		System.out.println(json);
		RUID deserialized = mapper.readValue(json, RUID.class);
		System.out.println(deserialized);
		assertEquals(original, deserialized);
	}


}