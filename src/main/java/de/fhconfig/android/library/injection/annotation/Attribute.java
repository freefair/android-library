package de.fhconfig.android.library.injection.annotation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
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
public @interface Attribute {
	@AttrRes
	public int id();

	public Type type();

	public enum Type {
		BOOLEAN(boolean.class),
		COLOR(int.class),
		COLOR_STATE_LIST(ColorStateList.class),
		DIMENSION(float.class),
		DIMENSION_PIXEL_OFFSET(int.class),
		DIMENSION_PIXEL_SIZE(int.class),
		DRAWABLE(Drawable.class),
		FLOAT(float.class),
		FRACTION(float.class),
		INT(int.class),
		INTEGER(int.class),
		LAYOUT_DIMENSION(int.class),
		RESOURCE_ID(int.class),
		STRING(String.class),
		TEXT(CharSequence.class),
		TEXT_ARRAY(CharSequence[].class),
		TYPED_VALUE(TypedValue.class);

		private Class<?> clazz;

		Type(Class<?> clazz){
			this.clazz = clazz;
		}

		public Class<?> getClazz() {
			return clazz;
		}
	}
}
