package de.fhconfig.android.library.annotations;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.fhconfig.android.binding.R;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DrawerToggle {

	@IdRes
	int value();
	@StringRes
	int openDrawerDescRes();
	@StringRes
	int closeDrawerDescRes();

}
