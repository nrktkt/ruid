package com.github.kag0.ruid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

/**
 * Created by nfischer on 9/17/2016.
 */
public class RUIDTest {
	@Test
	public void parse() throws Exception {
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

		assertEquals(sorted, shuffled.stream().sorted().collect(toList()));
	}



}