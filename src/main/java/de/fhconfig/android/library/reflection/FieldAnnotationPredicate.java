package de.fhconfig.android.library.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import de.fhconfig.android.library.predicate.Predicate;

/**
 * Created by larsgrefer on 16.03.15.
 */
public class FieldAnnotationPredicate extends Predicate<Field> {

	private Class<? extends Annotation> annotation;

	public FieldAnnotationPredicate(Class<? extends Annotation> annotation){
		this.annotation = annotation;
	}

	public Class<? extends Annotation> getAnnotation() {
		return annotation;
	}

	@Override
	public boolean apply(Field input) {
		return input.isAnnotationPresent(annotation);
	}
}
