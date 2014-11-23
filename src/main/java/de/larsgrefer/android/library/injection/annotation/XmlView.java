package de.larsgrefer.android.library.injection.annotation;

/**
 * Created by larsgrefer on 23.11.14.
 */
public @interface XmlView {
	public int id() default DEFAULT_ID;

	public static final int DEFAULT_ID = -1;
}
