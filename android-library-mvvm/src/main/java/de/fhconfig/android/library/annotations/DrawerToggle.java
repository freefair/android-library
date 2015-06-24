package de.fhconfig.android.library.annotations;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DrawerToggle {
	@IdRes
	int value();
	@StringRes
	int appName();
}
