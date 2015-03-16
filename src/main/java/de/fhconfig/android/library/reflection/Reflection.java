package de.fhconfig.android.library.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class Reflection {

	public static List<Field> getDeclaredFieldsWithAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation) {
		List<Field> fields = new ArrayList<>();

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotation)) {
				fields.add(field);
			}
		}

		return fields;
	}

	public static Class<?> getClassInClass(Class<?> rootClass, String innerClassName) {
		for (Class<?> clazz : rootClass.getClasses()) {
			if (clazz.getSimpleName().equals(innerClassName)) {
				return clazz;
			}
		}
		return null;
	}
}
