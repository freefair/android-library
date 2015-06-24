package de.fhconfig.android.library.util.predicate;

import org.junit.Test;

import static de.fhconfig.android.library.util.predicate.TruePredicate.getInstance;
import static org.junit.Assert.*;

public class TruePredicateTest {

	@Test
	public void testApply() throws Exception {
		TruePredicate truePredicate = getInstance();

		assertTrue(truePredicate.apply("Hallo"));
		assertTrue(truePredicate.apply(55));
	}

	@Test
	public void testNot() throws Exception {
		TruePredicate truePredicate = getInstance();

		assertFalse(truePredicate.not().apply("Hallo"));
		assertFalse(truePredicate.not().apply(55));
	}
}