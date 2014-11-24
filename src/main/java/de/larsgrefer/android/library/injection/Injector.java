package de.larsgrefer.android.library.injection;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import de.larsgrefer.android.library.injection.annotation.XmlLayout;
import de.larsgrefer.android.library.injection.annotation.XmlView;
import de.larsgrefer.android.library.reflection.Reflection;

/**
 * Created by larsgrefer on 23.11.14.
 */
public abstract class Injector<T> {

	private T object;
	private Class<?> rClass;

	public Injector(T object) {
		this.setObject(object);
	}

	public Injector(T object, Class<?> rClass){
		this.setObject(object);
		this.rClass = rClass;
	}

	public void setR(Class<?> r) {
		if(r.getSimpleName() != "R"){
			throw new IllegalArgumentException();
		}
		this.rClass = r;
	}

	protected Class<?> getRClass(){
		return rClass;
	}

	protected Class<?> getRid(){
		return Reflection.getClassInClass(getRClass(), "id");
	}

	protected boolean isRAvailable(){
		return getRClass() != null;
	}

	protected T getObject() {
		return object;
	}

	protected Class<T> getObjectClass(){
		return (Class<T>) getObject().getClass();
	}

	protected void setObject(T object){
		this.object = object;
		if(getObjectClass().isAnnotationPresent(XmlLayout.class)){
			XmlLayout xmlLayoutAnnotation = getObjectClass().getAnnotation(XmlLayout.class);
			setR(xmlLayoutAnnotation.r());
			setLayout(xmlLayoutAnnotation.id());
		}
	}

	protected abstract void setLayout(@LayoutRes int layoutId);


	public void injectViews() throws ViewIdNotFoundException {
		List<Field> viewFields = Reflection.getFieldsWithAnnotationPresent(getObjectClass(), XmlView.class);

		for(Field field : viewFields){
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

	protected abstract View findViewById(@IdRes int viewId);

	@IdRes
	protected int findViewId(Field field) throws ViewIdNotFoundException {
		int viewId = 0;

		if(field.isAnnotationPresent(XmlView.class))
		{
			XmlView xmlViewAnnotation = field.getAnnotation(XmlView.class);
			if(xmlViewAnnotation.id() != XmlView.DEFAULT_ID)
			{
				viewId = xmlViewAnnotation.id();
			}
		}

		String fieldName = field.getName();
		if(isRAvailable())
		{
			try {
				viewId = getRid().getDeclaredField(fieldName).getInt(null);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) { //that should never happen
				e.printStackTrace();
				throw new IllegalArgumentException("The field '" + fieldName + "' in the rIdClass class exists but was not accessible. Either the universe broke or you did not pass the 'R.id.class' as rIdClass", e);
			}
		}

		if( viewId == 0 ){
			throw new ViewIdNotFoundException("The ViewId for the field '" + field.toString() + "' could not be found.");
		}
		return viewId;
	}
}
