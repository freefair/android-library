package io.freefair.android.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import io.freefair.android.injection.annotation.AttributeType;
import io.freefair.android.injection.annotation.InjectAttribute;
import io.freefair.android.injection.annotation.InjectResource;
import io.freefair.android.injection.annotation.InjectView;
import io.freefair.android.injection.annotation.ResourceType;

import static org.junit.Assert.*;

public class ReflectionTest {

	@Test
	public void testGetDeclaredFields() throws Exception {
		List<Field> declaredFields = Reflection.getAllFields(ClassC.class, Object.class, new FieldAnnotationPredicate(InjectView.class));

		assertEquals(2, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, new FieldAnnotationPredicate(InjectAttribute.class));
		assertEquals(1, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, new FieldAnnotationPredicate(InjectResource.class));
		assertEquals(1, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, ClassB.class, new FieldAnnotationPredicate(InjectView.class));
		assertEquals(1, declaredFields.size());
	}

	class ClassA{
		@InjectView()
		private int aa;
		@InjectAttribute(id = 55, type = AttributeType.FLOAT)
		private double ab;
	}

	class ClassB extends ClassA{
		public float ba;
		@InjectResource(id = 44, type = ResourceType.MOVIE)
		public Object ab;
	}

	class ClassC extends ClassB{
		ClassA ca;
		@InjectView()
		ClassB cb;
	}
}