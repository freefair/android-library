package de.larsgrefer.android.library.injection.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by larsgrefer on 23.11.14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface XmlView {
	@IdRes
	public int id() default DEFAULT_ID;

	public static final int DEFAULT_ID = -1;
}
