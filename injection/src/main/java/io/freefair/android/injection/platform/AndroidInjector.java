package io.freefair.android.injection.platform;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.annotation.InjectAttribute;
import io.freefair.android.injection.annotation.InjectResource;
import io.freefair.android.injection.annotation.InjectView;
import io.freefair.android.injection.exceptions.InjectionException;
import io.freefair.android.injection.exceptions.ViewIdNotFoundException;
import io.freefair.android.injection.reflection.Reflection;
import io.freefair.android.util.Logger;
import io.freefair.android.util.Optional;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public abstract class AndroidInjector<T> extends Injector {

	private T object;
	private Class<?> rClass;
	private Logger log = Logger.forObject(this);

	public AndroidInjector(Injector parentInjector, T object, Class<?> rClass) {
		super(parentInjector);
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
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getObjectClass() {
		return (Class<T>) getObject().getClass();
	}

	@Override
	protected void inject(@NonNull Object instance, @NonNull Field field) {
		if (field.isAnnotationPresent(InjectView.class)) {
			getBinding().views.put(field, findViewId(field));
		} else if (field.isAnnotationPresent(InjectResource.class)) {
			getBinding().resources.put(field, field.getAnnotation(InjectResource.class));
		} else if (field.isAnnotationPresent(InjectAttribute.class)) {
			getBinding().attributes.put(field, field.getAnnotation(InjectAttribute.class));
		} else {
			super.inject(instance, field);
		}
	}

	public void injectViews() {
		for (Map.Entry<Field, Integer> viewBinding : getBinding().views.entrySet()) {
			View view = findViewById(viewBinding.getValue());
			inject(viewBinding.getKey(), view);
		}
	}


	protected void inject(Field field, Object value) {
		inject(getObject(), field, value);
	}

	public void injectAttributes() {

		int[] attrIds = new int[getBinding().attributes.size()];

		int i = 0;
		for (Map.Entry<Field, InjectAttribute> entry : getBinding().attributes.entrySet()) {
			InjectAttribute annotation = entry.getValue();
			attrIds[i] = annotation.id();

			Field field = entry.getKey();
			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}
			i++;
		}

		TypedArray typedArray = resolveValue(Resources.Theme.class, getObject()).obtainStyledAttributes(attrIds);

		int index = 0;
		for (Map.Entry<Field, InjectAttribute> entry : getBinding().attributes.entrySet()) {

			Field field = entry.getKey();
			field.setAccessible(true);
			InjectAttribute annotation = entry.getValue();

			try {
				switch (annotation.type()) {
					case BOOLEAN:
						boolean aBoolean = typedArray.getBoolean(index, false);
						field.setBoolean(object, aBoolean);
						break;
					case COLOR:
						int color = typedArray.getColor(index, 0);
						field.setInt(object, color);
						break;
					case COLOR_STATE_LIST:
						ColorStateList colorStateList = typedArray.getColorStateList(index);
						field.set(object, colorStateList);
						break;
					case DIMENSION:
						float dimension = typedArray.getDimension(index, 0f);
						field.setFloat(object, dimension);
						break;
					case DIMENSION_PIXEL_OFFSET:
						int dimensionPixelOffset = typedArray.getDimensionPixelOffset(index, 0);
						field.setInt(object, dimensionPixelOffset);
						break;
					case DIMENSION_PIXEL_SIZE:
						int dimensionPixelSize = typedArray.getDimensionPixelSize(index, 0);
						field.setInt(object, dimensionPixelSize);
						break;
					case DRAWABLE:
						Drawable drawable = typedArray.getDrawable(index);
						field.set(object, drawable);
						break;
					case FLOAT:
						float aFloat = typedArray.getFloat(index, 0f);
						field.setFloat(object, aFloat);
						break;
					case FRACTION:
						float fraction = typedArray.getFraction(index, 1, 1, 0f);
						field.setFloat(object, fraction);
						break;
					case INT:
						int anInt = typedArray.getInt(index, 0);
						field.setInt(object, anInt);
						break;
					case INTEGER:
						int integer = typedArray.getInteger(index, 0);
						field.setInt(object, integer);
						break;
					case LAYOUT_DIMENSION:
						int layoutDimension = typedArray.getLayoutDimension(index, 0);
						field.setInt(object, layoutDimension);
						break;
					case RESOURCE_ID:
						int resourceId = typedArray.getResourceId(index, 0);
						field.setInt(object, resourceId);
						break;
					case STRING:
						String string = typedArray.getString(index);
						field.set(object, string);
						break;
					case TEXT:
						CharSequence text = typedArray.getText(index);
						field.set(object, text);
						break;
					case TEXT_ARRAY:
						CharSequence[] textArray = typedArray.getTextArray(index);
						field.set(object, textArray);
						break;
					case TYPED_VALUE:
						TypedValue typedValue = Optional.ofNullable((TypedValue) field.get(object))
														.orElse(new TypedValue());
						typedArray.getValue(index, typedValue);
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
			index++;
		}
		typedArray.recycle();
	}

	@TargetApi(LOLLIPOP)
	public void injectResources() {

		Resources resources = resolveValue(Resources.class, getObject());
		if (resources == null)
			throw new InjectionException("Resources.class not found");

		for (Map.Entry<Field, InjectResource> resourceBinding : getBinding().resources.entrySet()) {
			Field field = resourceBinding.getKey();
			InjectResource annotation = resourceBinding.getValue();
			int resourceId = annotation.id();

			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}

			try {
				field.setAccessible(true);
				switch (annotation.type()) {
					case ANIMATION:
						XmlResourceParser animation = resources.getAnimation(resourceId);
						field.set(object, animation);
						break;
					case BOOLEAN:
						boolean aBoolean = resources.getBoolean(resourceId);
						field.setBoolean(object, aBoolean);
						break;
					case COLOR:
						int color = resources.getColor(resourceId);
						field.setInt(object, color);
						break;
					case COLOR_STATE_LIST:
						ColorStateList colorStateList = resources.getColorStateList(resourceId);
						field.set(object, colorStateList);
						break;
					case DIMENSION:
						float dimension = resources.getDimension(resourceId);
						field.setFloat(object, dimension);
						break;
					case DIMENSION_PIXEL_OFFSET:
						int dimensionPixelOffset = resources.getDimensionPixelOffset(resourceId);
						field.setInt(object, dimensionPixelOffset);
						break;
					case DIMENSION_PIXEL_SIZE:
						int dimensionPixelSize = resources.getDimensionPixelSize(resourceId);
						field.setInt(object, dimensionPixelSize);
						break;
					case DRAWABLE:
						Drawable drawable;
						if (SDK_INT >= LOLLIPOP) {
							drawable = resolveValue(Context.class, getObject()).getDrawable(resourceId);
						} else {
							drawable = resources.getDrawable(resourceId);
						}
						field.set(object, drawable);
						break;
					case FRACTION:
						float fraction = resources.getFraction(resourceId, 1, 1);
						field.setFloat(object, fraction);
						break;
					case INT_ARRAY:
						int[] intArray = resources.getIntArray(resourceId);
						field.set(object, intArray);
						break;
					case INTEGER:
						int integer = resources.getInteger(resourceId);
						field.setInt(field, integer);
						break;
					case LAYOUT:
						XmlResourceParser layout = resources.getLayout(resourceId);
						field.set(object, layout);
						break;
					case MOVIE:
						Movie movie = resources.getMovie(resourceId);
						field.set(object, movie);
						break;
					case STRING:
						String string = resources.getString(resourceId);
						field.set(object, string);
						break;
					case STRING_ARRAY:
						String[] stringArray = resources.getStringArray(resourceId);
						field.set(object, stringArray);
						break;
					case TEXT:
						CharSequence text = resources.getText(resourceId);
						field.set(object, text);
						break;
					case TEXT_ARRAY:
						CharSequence[] textArray = resources.getTextArray(resourceId);
						field.set(object, textArray);
						break;
					case TYPED_VALUE:
						TypedValue typedValue = Optional.ofNullable((TypedValue) field.get(object))
														.orElse(new TypedValue());
						resources.getValue(resourceId, typedValue, true);
						field.set(object, typedValue);
						break;
					case XML:
						XmlResourceParser xml = resources.getXml(resourceId);
						field.set(object, xml);
						break;
					default:
						log.error("Unkown resource type at field " + field.getName());
						break;
				}
			} catch (IllegalAccessException iae) {
				log.error(iae.getMessage(), iae);
			}
		}
	}

	private void logFieldTypeMissmatch(Field field, Class<?> clazz) {
		log.warn("Possible field type missmatch at Field " + field.toString() + ". Going to inject type " + clazz.getName() + " but Field type is " + field.getType().getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {

		if (type.isAssignableFrom(getObjectClass()))
			return (T) getObject();

		if (type.isAssignableFrom(Resources.Theme.class))
			return (T) resolveValue(Context.class, instance).getTheme();

		if (type.isAssignableFrom(Resources.class))
			return (T) resolveValue(Context.class, instance).getResources();

		return super.resolveValue(type, instance);
	}

	protected abstract View findViewById(@IdRes int viewId);

	@IdRes
	protected int findViewId(Field field) throws ViewIdNotFoundException {

		if (field.isAnnotationPresent(InjectView.class)) {
			InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
			if (injectViewAnnotation.value() != InjectView.DEFAULT_ID) {
				return injectViewAnnotation.value();
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

	private Binding getBinding() {
		if (bindings.get(getObjectClass()) == null) {
			bindings.put(getObjectClass(), new Binding());
		}
		return bindings.get(getObjectClass());
	}

	private static WeakHashMap<Class<?>, Binding> bindings = new WeakHashMap<>();

	public static class Binding {
		Map<Field, Integer> views;
		Map<Field, InjectAttribute> attributes;
		Map<Field, InjectResource> resources;

		Binding() {
			views = new HashMap<>();
			attributes = new HashMap<>();
			resources = new HashMap<>();
		}
	}

}
