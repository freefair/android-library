package de.larsgrefer.android.library.injection;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

import de.larsgrefer.android.library.injection.annotation.XmlLayout;
import de.larsgrefer.android.library.injection.annotation.XmlMenu;
import de.larsgrefer.android.library.injection.annotation.XmlView;
import de.larsgrefer.android.library.reflection.Reflection;

/**
 * Created by larsgrefer on 23.11.14.
 */
public abstract class XmlInjector<T> {

	private T object;
	private Class<?> rClass;

	public XmlInjector(T object, Class<?> rClass){
		this.setObject(object);
		this.rClass = rClass;
	}

	public void setR(Class<?> r) {
		if(!r.getSimpleName().equals("R")){
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
			setLayoutId(getObjectClass().getAnnotation(XmlLayout.class).value());
		}
		if(getObjectClass().isAnnotationPresent(XmlMenu.class)){
			setMenuId(getObjectClass().getAnnotation(XmlMenu.class).value());
		}
	}

	@LayoutRes
	private int layoutId;

	public void setLayoutId(@LayoutRes int layoutId){
		this.layoutId = layoutId;
	}

	@LayoutRes
	public int getLayoutId(){
		return this.layoutId;
	}

	@MenuRes
	private int menuId;

	public void setMenuId(@MenuRes int menuId){
		this.menuId = menuId;
	}

	@MenuRes
	public int getMenuId(){
		return this.menuId;
	}

	public void injectViews() throws ViewIdNotFoundException {
		List<Field> viewFields = Reflection.getDeclaredFieldsWithAnnotationPresent(getObjectClass(), XmlView.class);

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
			if(xmlViewAnnotation.value() != XmlView.DEFAULT_ID)
			{
				viewId = xmlViewAnnotation.value();
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
				throw new RuntimeException(e);
			}
		}

		if( viewId == 0 ){
			throw new ViewIdNotFoundException(fieldName);
		}
		return viewId;
	}

}
