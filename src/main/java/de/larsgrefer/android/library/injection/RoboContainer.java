package de.larsgrefer.android.library.injection;

import android.util.Log;
import de.larsgrefer.android.library.injection.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoboContainer
{
	private Map<Class<?>, RoboRegistration<?>> _map = new HashMap<>();

	public RoboContainer(ArrayList<RoboRegistration<?>> registrations) {
		for(RoboRegistration<?> registration : registrations)
		{
			_map.put(registration.getToType(), registration);
		}
	}

	public <TType> TType resolve(Class<TType> clazz)
	{
		if(!_map.containsKey(clazz))
		{
			if(!clazz.isInterface() && !clazz.isAnnotation() && !clazz.isSynthetic())
			{
				RoboRegistration<TType> tTypeRoboRegistration = new RoboRegistration<>(clazz);
				tTypeRoboRegistration.toSelf();
				_map.put(clazz, tTypeRoboRegistration);
			}
		}
		return (TType) _map.get(clazz).getInstance(this);
	}

	public void inject(Object obj)
	{
		Class<?> aClass = obj.getClass();
		Field[] fields = aClass.getFields();
		for(Field field : fields)
		{
			Inject annotation = field.getAnnotation(Inject.class);
			if(annotation != null)
			{
				try {
					field.set(obj, resolve(field.getType()));
				} catch (IllegalAccessException e) {
					Log.e(this.getClass().getName(), "Could not inject field " + field.getName(), e);
				}
			}
		}
	}
}
