package io.freefair.android.injection.annotation;

import android.support.annotation.MenuRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Inherited
public @interface XmlMenu {
	@MenuRes int value();
}
