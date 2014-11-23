package de.larsgrefer.android.library.injection;

import android.app.Activity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import de.larsgrefer.android.library.injection.annotation.XmlView;
import de.larsgrefer.android.library.reflection.Reflection;

/**
 * Created by larsgrefer on 23.11.14.
 */
public abstract class Injector<T> {

	private Class<?> r;

	public void setR(Class<?> r) {
		if(r.getSimpleName() != "R"){
			throw new IllegalArgumentException();
		}
		this.r = r;
	}

	protected Class<?> getR(){
		return r;
	}

	protected Class<?> getRid(){
		return Reflection.getClassInClass(R, "id");
	}

	protected boolean isRAvailable(){
		return getR() != null;
	}

	public static void injectViews(T activity, Class<?> idClass) {
		Class<? extends Activity> activityClass = activity.getClass();
		List<Field> viewFields = Reflection.getFieldsWithAnnotationPresent(activityClass, XmlView.class);

		for(Field f : viewFields){
			f.setAccessible(true);

			findView(f, activity, idClass);
		}
	}

	protected int findViewId(Field field) throws ViewIdNotFoundException {
		Objects.requireNonNull(field);
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
				rIdClass.getDeclaredField(fieldName).getInt(null);
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
