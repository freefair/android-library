package de.fhconfig.android.library.injection.annotation;

import android.support.annotation.AnyRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by larsgrefer on 24.02.15.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectResource {
	@AnyRes int id();

	ResourceType type();

}
