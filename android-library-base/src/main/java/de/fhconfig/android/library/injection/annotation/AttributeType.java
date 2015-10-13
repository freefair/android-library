package de.fhconfig.android.library.injection.annotation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Types of attributes which may be injected using {@link InjectAttribute}
 */
public enum AttributeType {
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

	AttributeType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
