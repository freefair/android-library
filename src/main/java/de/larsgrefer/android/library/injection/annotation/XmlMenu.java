package de.larsgrefer.android.library.injection.annotation;

import android.support.annotation.MenuRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by larsgrefer on 27.11.14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface XmlMenu {
	@MenuRes
	public int value();
}
