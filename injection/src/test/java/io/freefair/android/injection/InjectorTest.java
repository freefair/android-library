package io.freefair.android.injection;

import org.junit.Before;
import org.junit.Test;

import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.util.function.Suppliers;

import static org.junit.Assert.*;

/**
 * Created by larsgrefer on 12.10.15.
 */
public class InjectorTest {

	InjectionContainer injector;

	@Before
	public void setUp(){
		injector = InjectionContainer.getInstance();
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

	@Test
	public void testConstructorInjection(){
		C c = new C();

		injector.inject(c);
	}

	public static class C {
		@Inject
		ConstructorInjectionTest cit;

		public ConstructorInjectionTest getCit() {
			return cit;
		}
	}

	public static class ConstructorInjectionTest {

		@Inject
		public ConstructorInjectionTest(A a, B b, String string){
			assertNotNull(a);
			assertNotNull(b);
			assertNotNull(string);

			assertEquals(string, b.getTest());
			assertEquals(string, a.getB().getTest());
		}
	}

	@Test
	public void testCircleInjection(){
		Circle circle = new Circle();

		injector.inject(circle);
	}

	public static class Circle{
		@Inject
		Circle1 c;
	}

	public static class Circle1{
		@Inject
		Circle2 c2;
	}

	public static class Circle2{
		@Inject
		Circle1 c1;
	}

}