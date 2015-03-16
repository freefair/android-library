package de.larsgrefer.android.library.injection.annotation;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.util.TypedValue;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by larsgrefer on 24.02.15.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Resource {
	@AnyRes
	public int id();

	public Resource.Type type();

	public enum Type{
		ANIMATION(XmlResourceParser.class),
		BOOLEAN(boolean.class),
		COLOR(int.class),
		COLOR_STATE_LIST(ColorStateList.class),
		DIMENSION(float.class),
		DIMENSION_PIXEL_OFFSET(int.class),
		DIMENSION_PIXEL_SIZE(int.class),
		DRAWABLE(Drawable.class),
		FRACTION(float.class),
		INT_ARRAY(int[].class),
		INTEGER(int.class),
		LAYOUT(XmlResourceParser.class),
		MOVIE(Movie.class),
		STRING(String.class),
		STRING_ARRAY(String[].class),
		TEXT(CharSequence.class),
		TEXT_ARRAY(CharSequence[].class),
		TYPED_VALUE(TypedValue.class),
		XML(XmlResourceParser.class);

		private Class<?> clazz;

		Type(Class<?> type){
			this.clazz = type;
		}

		public Class<?> getClazz() {
			return clazz;
		}
	}
}
