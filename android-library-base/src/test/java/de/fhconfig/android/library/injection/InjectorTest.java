package de.fhconfig.android.library.injection;

import org.junit.Before;
import org.junit.Test;

import de.fhconfig.android.library.injection.annotation.Inject;
import de.fhconfig.android.library.util.Suppliers;

import static org.junit.Assert.*;

/**
 * Created by larsgrefer on 12.10.15.
 */
public class InjectorTest {

	InjectionContainer injector;

	@Before
	public void setUp(){
		injector = new InjectionContainer(null);
		injector.registerSupplier(String.class, Suppliers.of("FOO"));
	}

	@Test
	public void testInjection(){

		A a = new A();

		injector.inject(a);

		assertEquals("FOO", a.getB().getTest());

	}


	public static class A {

		@Inject
		private B b;

		public B getB() {
			return b;
		}
	}

	public static class B {

		@Inject
		private String test;

		public String getTest() {
			return test;
		}
	}

}