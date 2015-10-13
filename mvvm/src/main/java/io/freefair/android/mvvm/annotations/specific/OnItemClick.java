package io.freefair.android.mvvm.annotations.specific;

import android.support.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.freefair.android.mvvm.annotations.specific.EventNames.OnItemClick;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
@EventName(OnItemClick)
public @interface OnItemClick
{
	@IdRes
	int value();
}
