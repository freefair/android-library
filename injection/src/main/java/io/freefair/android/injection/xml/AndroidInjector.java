package io.freefair.android.injection.xml;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

import io.freefair.android.injection.reflection.FieldAnnotationPredicate;
import io.freefair.android.injection.reflection.Reflection;
import io.freefair.android.util.Logger;
import io.freefair.android.injection.Injector;
import io.freefair.android.injection.annotation.InjectAttribute;
import io.freefair.android.injection.annotation.InjectResource;
import io.freefair.android.injection.annotation.InjectView;
import io.freefair.android.injection.annotation.XmlLayout;
import io.freefair.android.injection.exceptions.ViewIdNotFoundException;
import io.freefair.android.util.Optional;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public abstract class AndroidInjector<T> extends Injector {

	private T object;
	private Class<?> rClass;
	private Logger log = Logger.forObject(this);
	@LayoutRes
	private int layoutId;

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
		if (getObjectClass().isAnnotationPresent(XmlLayout.class)) {
			setLayoutId(getObjectClass().getAnnotation(XmlLayout.class).value());
		}
	}

	@SuppressWarnings("unchecked")
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

	public void injectViews() throws ViewIdNotFoundException {
		List<Field> viewFields = Reflection.getAllFields(getObjectClass(), new FieldAnnotationPredicate(InjectView.class));

		for (Field field : viewFields) {
			injectView(field);
		}
	}

	private void injectView(Field field) {
		int viewId = findViewId(field);
		View view = findViewById(viewId);
		inject(field, view);
	}

	@Override
	protected void inject(@NonNull Object instance, @NonNull Field field) {
		super.inject(instance, field);
	}

	protected void inject(Field field, Object value) {
		inject(getObject(), field, value);
	}

	public void injectAttributes() {
		log.debug("injectAttributes()");
		List<Field> attrFields = Reflection.getAllFields(getObjectClass(), new FieldAnnotationPredicate(InjectAttribute.class));
		log.debug(attrFields.toString());
		int[] attrIds = new int[attrFields.size()];

		for (int i = 0; i < attrFields.size(); i++) {

			Field field = attrFields.get(i);
			InjectAttribute annotation = field.getAnnotation(InjectAttribute.class);
			attrIds[i] = annotation.id();

			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}
		}

		TypedArray typedArray = resolveValue(Resources.Theme.class, getObject()).obtainStyledAttributes(attrIds);

		for (int index = 0; index < attrFields.size(); index++) {

			Field field = attrFields.get(index);
			field.setAccessible(true);
			InjectAttribute annotation = field.getAnnotation(InjectAttribute.class);

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
						TypedValue typedValue = Optional.ofNullable((TypedValue) field.get(object))
														.orElse(new TypedValue());
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

	private void logFieldInjection(Field field, int newValue) {
		log.debug("Injecting value " + newValue + " into field " + field.getName());
	}

	private void logFieldInjection(Field field, float newValue) {
		log.debug("Injecting value " + newValue + " into field " + field.getName());
	}

	private void logFieldInjection(Field field, Object newValue) {
		log.debug("Injecting value " + newValue.toString() + " into field " + field.getName());
	}

	@TargetApi(LOLLIPOP)
	public void injectResources() {
		log.debug("injectResources()");
		List<Field> resourceFields = Reflection.getAllFields(getObjectClass(), Object.class, new FieldAnnotationPredicate(InjectResource.class));
		log.debug(resourceFields.toString());

		Resources resources = resolveValue(Resources.class, getObject());
		for (Field field : resourceFields) {
			InjectResource annotation = field.getAnnotation(InjectResource.class);
			int resourceId = annotation.id();

			if (!field.getType().isAssignableFrom(annotation.type().getClazz())) {
				logFieldTypeMissmatch(field, annotation.type().getClazz());
			}

			try {
				field.setAccessible(true);
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
							drawable = resolveValue(Context.class, getObject()).getDrawable(resourceId);
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
						TypedValue typedValue = Optional.ofNullable((TypedValue) field.get(object))
														.orElse(new TypedValue());
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
				log.error(iae.getMessage(), iae);
			}
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {

		if(type.isAssignableFrom(getObjectClass()))
			return (T) getObject();

		if(type.isAssignableFrom(Resources.Theme.class))
			return (T) resolveValue(Context.class, instance).getTheme();

		if(type.isAssignableFrom(Resources.class))
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

}
