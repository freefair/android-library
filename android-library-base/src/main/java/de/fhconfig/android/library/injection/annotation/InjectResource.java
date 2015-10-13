package de.fhconfig.android.library.injection.annotation;

import android.support.annotation.AnyRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectResource {
	@AnyRes int id();

	/**
	 * The type of resource to inject
	 */
	ResourceType type();
}
