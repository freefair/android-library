package de.fhconfig.android.library.reflection;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import de.fhconfig.android.library.injection.annotation.Attribute;
import de.fhconfig.android.library.injection.annotation.Resource;
import de.fhconfig.android.library.injection.annotation.XmlView;

import static org.junit.Assert.*;

public class ReflectionTest {

	@Test
	public void testGetDeclaredFields() throws Exception {
		List<Field> declaredFields = Reflection.getAllFields(ClassC.class, Optional.<Class<? super ClassC>>of(Object.class), new Predicate<Field>() {
			@Override
			public boolean apply(Field input) {
				return input.isAnnotationPresent(XmlView.class);
			}
		});

		assertEquals(2, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, new FieldAnnotationPredicate(Attribute.class));
		assertEquals(1, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, new FieldAnnotationPredicate(Resource.class));
		assertEquals(1, declaredFields.size());

		declaredFields = Reflection.getAllFields(ClassC.class, ClassB.class, new FieldAnnotationPredicate(XmlView.class));
		assertEquals(1, declaredFields.size());
	}

	class ClassA{
		@XmlView()
		private int aa;
		@Attribute(id = 55, type = Attribute.Type.FLOAT)
		private double ab;
	}

	class ClassB extends ClassA{
		public float ba;
		@Resource(id = 44, type = Resource.Type.MOVIE)
		public Object ab;
	}

	class ClassC extends ClassB{
		ClassA ca;
		@XmlView()
		ClassB cb;
	}
}