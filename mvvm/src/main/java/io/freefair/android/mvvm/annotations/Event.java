package io.freefair.android.mvvm.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.freefair.android.mvvm.annotations.specific.EventNames;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event
{
	@IdRes
	int value();
	EventNames event() default EventNames.NONE;
	boolean required() default false;
}
