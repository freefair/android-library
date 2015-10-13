package io.freefair.android.mvvm.annotations;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface DrawerToggle {

	@IdRes
	int value();
	@StringRes
	int openDrawerDescRes();
	@StringRes
	int closeDrawerDescRes();

}
