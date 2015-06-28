package de.fhconfig.android.library.injection.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by larsgrefer on 28.06.15.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface InjectAnnotation {

	Class<Override> DEFAULT = Override.class;

	Class<? extends Annotation> value() default Override.class;
}
