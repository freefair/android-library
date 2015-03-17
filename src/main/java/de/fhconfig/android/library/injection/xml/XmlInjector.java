package de.fhconfig.android.library.injection.xml;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.util.TypedValue;
import android.view.View;

import com.google.common.base.Optional;

import java.lang.reflect.Field;
import java.util.List;

import de.fhconfig.android.library.Logger;
import de.fhconfig.android.library.injection.annotation.XmlLayout;
import de.fhconfig.android.library.injection.annotation.XmlMenu;
import de.fhconfig.android.library.injection.annotation.XmlView;
import de.fhconfig.android.library.injection.exceptions.ViewIdNotFoundException;
import de.fhconfig.android.library.reflection.FieldAnnotationPredicate;
import de.fhconfig.android.library.reflection.Reflection;
import de.fhconfig.android.library.injection.annotation.Attribute;
import de.fhconfig.android.library.injection.annotation.Resource;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public abstract class XmlInjector<T> {

	private T object;
	private Class<?> rClass;
	private Logger log = Logger.forObject(this);
	@LayoutRes
	private int layoutId;
	@MenuRes
	private int menuId;

	public XmlInjector(T object, Class<?> rClass) {
		this.setObject(object);
		this.rClass = rClass;
	}

	public void setR(Class<?> r) {
		if (!r.getSimpleName().equals("R")) {
			throw new IllegalArgumentException();
		}
		this.rClass = r;
	}

	protected Class<?> getRClass() {
		return rClass;
	}

	protected Class<?> getRid() {
		return Reflection.getClassInClass(getRClass(), "id");
	}

	protected boolean isRAvailable() {
		return getRClass() != null;
	}

	protected T getObject() {
		return object;
	}

	protected void setObject(T object) {
		this.object = object;
		if (getObjectClass().isAnnotationPresent(XmlLayout.class)) {
			setLayoutId(getObjectClass().getAnnotation(XmlLayout.class).value());
		}
		if (getObjectClass().isAnnotationPresent(XmlMenu.class)) {
			setMenuId(getObjectClass().getAnnotation(XmlMenu.class).value());
		}
	}

	protected Class<T> getObjectClass() {
		return (Class<T>) getObject().getClass();
	}

	@LayoutRes
	public int getLayoutId() {
		return this.layoutId;
	}

	public void setLayoutId(@LayoutRes int layoutId) {
		this.layoutId = layoutId;
	}

	@MenuRes
	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(@MenuRes int menuId) {
		this.menuId = menuId;
	}

	public void injectViews() throws ViewIdNotFoundException {
		List<Field> viewFields = Reflection.getAllFields(getObjectClass(), new FieldAnnotationPredicate(XmlView.class));

		for (Field field : viewFields) {
			field.setAccessible(true);

			int viewId = findViewId(field);
			View view = findViewById(viewId);
			try {
				field.set(object, view);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void injectAttributes() {
		log.debug("injectAttributes()");
		List<Field> attrFields = Reflection.getAllFields(getObjectClass(), new FieldAnnotationPredicate(Attribute.class));
		log.debug(attrFields.toString());
		int[] attrIds = new int[attrFields.size()];

		for (int i = 0; i < attrFields.size(); i++) {

			Field field = attrFields.get(i);
			Attribute annotation = field.getAnnotation(Attribute.class);
			attrIds[i] = annotation.id();

			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}
		}

		TypedArray typedArray = getTheme().obtainStyledAttributes(attrIds);

		for (int index = 0; index < attrFields.size(); index++) {

			Field field = attrFields.get(index);
			field.setAccessible(true);
			Attribute annotation = field.getAnnotation(Attribute.class);

			try {
				switch (annotation.type()) {

					case BOOLEAN:
						boolean aBoolean = typedArray.getBoolean(index, false);
						logFieldInjection(field, aBoolean);
						field.setBoolean(object, aBoolean);
						break;
					case COLOR:
						int color = typedArray.getColor(index, 0);
						logFieldInjection(field, color);
						field.setInt(object, color);
						break;
					case COLOR_STATE_LIST:
						ColorStateList colorStateList = typedArray.getColorStateList(index);
						logFieldInjection(field, colorStateList);
						field.set(object, colorStateList);
						break;
					case DIMENSION:
						float dimension = typedArray.getDimension(index, 0f);
						logFieldInjection(field, dimension);
						field.setFloat(object, dimension);
						break;
					case DIMENSION_PIXEL_OFFSET:
						int dimensionPixelOffset = typedArray.getDimensionPixelOffset(index, 0);
						logFieldInjection(field, dimensionPixelOffset);
						field.setInt(object, dimensionPixelOffset);
						break;
					case DIMENSION_PIXEL_SIZE:
						int dimensionPixelSize = typedArray.getDimensionPixelSize(index, 0);
						logFieldInjection(field, dimensionPixelSize);
						field.setInt(object, dimensionPixelSize);
						break;
					case DRAWABLE:
						Drawable drawable = typedArray.getDrawable(index);
						logFieldInjection(field, drawable);
						field.set(object, drawable);
						break;
					case FLOAT:
						float aFloat = typedArray.getFloat(index, 0f);
						logFieldInjection(field, aFloat);
						field.setFloat(object, aFloat);
						break;
					case FRACTION:
						float fraction = typedArray.getFraction(index, 1, 1, 0f);
						field.setFloat(object, fraction);
						break;
					case INT:
						int anInt = typedArray.getInt(index, 0);
						logFieldInjection(field, anInt);
						field.setInt(object, anInt);
						break;
					case INTEGER:
						int integer = typedArray.getInteger(index, 0);
						logFieldInjection(field, integer);
						field.setInt(object, integer);
						break;
					case LAYOUT_DIMENSION:
						int layoutDimension = typedArray.getLayoutDimension(index, 0);
						logFieldInjection(field, layoutDimension);
						field.setInt(object, layoutDimension);
						break;
					case RESOURCE_ID:
						int resourceId = typedArray.getResourceId(index, 0);
						logFieldInjection(field, resourceId);
						field.setInt(object, resourceId);
						break;
					case STRING:
						String string = typedArray.getString(index);
						logFieldInjection(field, string);
						field.set(object, string);
						break;
					case TEXT:
						CharSequence text = typedArray.getText(index);
						logFieldInjection(field, text);
						field.set(object, text);
						break;
					case TEXT_ARRAY:
						CharSequence[] textArray = typedArray.getTextArray(index);
						logFieldInjection(field, textArray);
						field.set(object, textArray);
						break;
					case TYPED_VALUE:
						TypedValue typedValue = Optional.fromNullable((TypedValue) field.get(object))
														.or(new TypedValue());
						typedArray.getValue(index, typedValue);
						logFieldInjection(field, typedValue);
						field.set(object, typedValue);
						break;
					default:
						log.error("Unkown resource type at field " + field.getName());
						break;
				}
			} catch (IllegalAccessException iae) {
				log.error(iae.getMessage());
				iae.printStackTrace();
			}
		}
		typedArray.recycle();
	}

	private void logFieldTypeMissmatch(Field field, Class<?> clazz) {
		log.warn("Possible field type missmatch at Field " + field.toString() + ". Going to inject type " + clazz.getName() + " but Field type is " + field.getType().getName());
	}

	private void logFieldInjection(Field field, int newValue){
		log.debug("Injecting value " + newValue + " into field " + field.getName());
	}

	private void logFieldInjection(Field field, float newValue){
		log.debug("Injecting value " + newValue + " into field " + field.getName());
	}

	private void logFieldInjection(Field field, Object newValue){
		log.debug("Injecting value " + newValue.toString() + " into field " + field.getName());
	}

	public void injectResources() {
		log.debug("injectResources()");
		List<Field> resourceFields = Reflection.getAllFields(getObjectClass(), Object.class, new FieldAnnotationPredicate(Resource.class));
		log.debug(resourceFields.toString());

		Resources resources = getResources();
		for (Field field : resourceFields) {
			Resource annotation = field.getAnnotation(Resource.class);
			int resourceId = annotation.id();

			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}

			try {

				switch (annotation.type()) {
					case ANIMATION:
						XmlResourceParser animation = resources.getAnimation(resourceId);
						logFieldInjection(field, animation);
						field.set(object, animation);
						break;
					case BOOLEAN:
						boolean aBoolean = resources.getBoolean(resourceId);
						logFieldInjection(field, aBoolean);
						field.setBoolean(object, aBoolean);
						break;
					case COLOR:
						int color = resources.getColor(resourceId);
						logFieldInjection(field, color);
						field.setInt(object, color);
						break;
					case COLOR_STATE_LIST:
						ColorStateList colorStateList = resources.getColorStateList(resourceId);
						logFieldInjection(field, colorStateList);
						field.set(object, colorStateList);
						break;
					case DIMENSION:
						float dimension = resources.getDimension(resourceId);
						logFieldInjection(field, dimension);
						field.setFloat(object, dimension);
						break;
					case DIMENSION_PIXEL_OFFSET:
						int dimensionPixelOffset = resources.getDimensionPixelOffset(resourceId);
						logFieldInjection(field, dimensionPixelOffset);
						field.setInt(object, dimensionPixelOffset);
						break;
					case DIMENSION_PIXEL_SIZE:
						int dimensionPixelSize = resources.getDimensionPixelSize(resourceId);
						logFieldInjection(field, dimensionPixelSize);
						field.setInt(object, dimensionPixelSize);
						break;
					case DRAWABLE:
						Drawable drawable;
						if (SDK_INT >= LOLLIPOP) {
							drawable = getContext().getDrawable(resourceId);
						} else {
							drawable = resources.getDrawable(resourceId);
						}
						logFieldInjection(field, drawable);
						field.set(object, drawable);
						break;
					case FRACTION:
						float fraction = resources.getFraction(resourceId, 1, 1);
						logFieldInjection(field, fraction);
						field.setFloat(object, fraction);
						break;
					case INT_ARRAY:
						int[] intArray = resources.getIntArray(resourceId);
						logFieldInjection(field, intArray);
						field.set(object, intArray);
						break;
					case INTEGER:
						int integer = resources.getInteger(resourceId);
						logFieldInjection(field, integer);
						field.setInt(field, integer);
						break;
					case LAYOUT:
						XmlResourceParser layout = resources.getLayout(resourceId);
						logFieldInjection(field, layout);
						field.set(object, layout);
						break;
					case MOVIE:
						Movie movie = resources.getMovie(resourceId);
						logFieldInjection(field, movie);
						field.set(object, movie);
						break;
					case STRING:
						String string = resources.getString(resourceId);
						logFieldInjection(field, string);
						field.set(object, string);
						break;
					case STRING_ARRAY:
						String[] stringArray = resources.getStringArray(resourceId);
						logFieldInjection(field, stringArray);
						field.set(object, stringArray);
						break;
					case TEXT:
						CharSequence text = resources.getText(resourceId);
						logFieldInjection(field, text);
						field.set(object, text);
						break;
					case TEXT_ARRAY:
						CharSequence[] textArray = resources.getTextArray(resourceId);
						logFieldInjection(field, textArray);
						field.set(object, textArray);
						break;
					case TYPED_VALUE:
						TypedValue typedValue = Optional.fromNullable((TypedValue) field.get(object))
														.or(new TypedValue());
						resources.getValue(resourceId, typedValue, true);
						logFieldInjection(field, typedValue);
						field.set(object, typedValue);
						break;
					case XML:
						XmlResourceParser xml = resources.getXml(resourceId);
						logFieldInjection(field, xml);
						field.set(object, xml);
						break;
					default:
						log.error("Unkown resource type at field " + field.getName());
						break;
				}
			} catch (IllegalAccessException iae) {
				log.error(iae.getMessage());
				iae.printStackTrace();
			}
		}
	}

	protected abstract Context getContext();

	protected Resources.Theme getTheme() {
		return getContext().getTheme();
	}

	protected Resources getResources() {
		return getContext().getResources();
	}

	protected abstract View findViewById(@IdRes int viewId);

	@IdRes
	protected int findViewId(Field field) throws ViewIdNotFoundException {

		if (field.isAnnotationPresent(XmlView.class)) {
			XmlView xmlViewAnnotation = field.getAnnotation(XmlView.class);
			if (xmlViewAnnotation.value() != XmlView.DEFAULT_ID) {
				return xmlViewAnnotation.value();
			}
		}

		String fieldName = field.getName();
		if (isRAvailable()) {
			try {
				return getRid().getDeclaredField(fieldName).getInt(null);
			} catch (NoSuchFieldException e) {
				log.info("Field " + fieldName + " not found in R class");
			} catch (IllegalAccessException e) { //that should never happen
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		throw new ViewIdNotFoundException(fieldName);
	}

}
