package de.fhconfig.android.library.reflection;

import com.google.common.base.Predicate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by larsgrefer on 16.03.15.
 */
public class FieldAnnotationPredicate implements Predicate<Field> {

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
