package de.fhconfig.android.library.injection.annotation;

import android.support.annotation.MenuRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by larsgrefer on 27.11.14.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
public @interface XmlMenu {
	@MenuRes int value();
}
