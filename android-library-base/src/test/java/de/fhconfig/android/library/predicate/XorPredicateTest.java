package de.fhconfig.android.library.util.predicate;

import org.junit.Test;

import static org.junit.Assert.*;

public class XorPredicateTest {

	@Test
	public void testApply() throws Exception {
		XorPredicate<String> xorPredicate1 = new XorPredicate<>(TruePredicate.getInstance(), TruePredicate.getInstance());
		XorPredicate<String> xorPredicate2 = new XorPredicate<>(TruePredicate.getInstance(), FalsePredicate.getInstance());
		XorPredicate<String> xorPredicate3 = new XorPredicate<>(FalsePredicate.getInstance(), TruePredicate.getInstance());
		XorPredicate<String> xorPredicate4 = new XorPredicate<>(FalsePredicate.getInstance(), FalsePredicate.getInstance());

		assertFalse(xorPredicate1.apply(""));
		assertTrue(xorPredicate2.apply(""));
		assertTrue(xorPredicate3.apply(""));
		assertFalse(xorPredicate4.apply(""));

	}
}