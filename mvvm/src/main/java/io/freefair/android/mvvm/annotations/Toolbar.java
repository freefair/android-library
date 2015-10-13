package io.freefair.android.mvvm.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Toolbar {
	@IdRes
	int value();
	boolean homeAsUp() default false;
	boolean homeButton() default false;
}
