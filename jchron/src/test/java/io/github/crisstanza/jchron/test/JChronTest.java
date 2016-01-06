package io.github.crisstanza.jchron.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.github.crisstanza.jchron.JChron;

@RunWith(Parameterized.class)
public final class JChronTest {

	final int input;
	final String expected;

	/**
	 * Retorna uma lista de dados de teste, no formato { input, expected }.
	 */
	@Parameters
	public static final Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		{
			data.add(new Object[] { 0, "[ 0 secs ]" });
			data.add(new Object[] { 500, "[ 0.5 secs ]" });
			data.add(new Object[] { 59 * 1000, "[ 59 secs ]" });
			data.add(new Object[] { 60 * 1000, "[ 1 min 0 secs ]" });
			data.add(new Object[] { 61 * 1000, "[ 1 min 1 sec ]" });
			data.add(new Object[] { 60 * 1000 * 60, "[ 1 hour 0 mins 0 secs ]" });
		}
		return data;
	}

	public JChronTest(final int input, final String expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void test() {
		final String actual = JChron.remainder(input);
		Assert.assertEquals(expected, actual);
	}

}
