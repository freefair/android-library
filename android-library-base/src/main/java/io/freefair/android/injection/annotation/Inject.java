package io.freefair.android.injection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR})
@Retention(RUNTIME)
public @interface Inject {
	/**
	 * The desired type of object, that should be injected into this field.
	 *
	 * If not specified, the injector will try to guess the desired type based on the fields type
	 */
	Class<?> value() default Object.class;

	/**
	 * If true, the injecton won't fail, if the dependency cannot be satisfied
	 * @return whether this dependency is optional or not
	 */
	boolean optional() default false;
}
