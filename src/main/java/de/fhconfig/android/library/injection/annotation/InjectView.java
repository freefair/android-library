package de.fhconfig.android.library.injection.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by larsgrefer on 23.11.14.
 */
@Target(FIELD)
@Retention(RUNTIME)
@Inherited
public @interface InjectView {
	@IdRes int value() default DEFAULT_ID;

	int DEFAULT_ID = -1;
}
