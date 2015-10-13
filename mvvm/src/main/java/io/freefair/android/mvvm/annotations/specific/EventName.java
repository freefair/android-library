package io.freefair.android.mvvm.annotations.specific;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface EventName
{
	EventNames value();
	boolean required() default false;
}
