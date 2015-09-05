package de.fhconfig.android.library.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.fhconfig.android.library.annotations.specific.EventNames;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event
{
	@IdRes
	int value();
	EventNames event() default EventNames.NONE;
}
