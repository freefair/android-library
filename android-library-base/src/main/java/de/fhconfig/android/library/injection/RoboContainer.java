package de.fhconfig.android.library.injection;

import android.content.Context;
import android.util.Log;
import de.fhconfig.android.library.injection.annotation.Inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoboContainer
{
	private Map<Class<?>, RoboRegistration<?>> _map = new HashMap<>();
	private ArrayList<IRoboFactory> factories;
	private Context context;

	public RoboContainer(ArrayList<RoboRegistration<?>> registrations, ArrayList<IRoboFactory> factories) {
		this.factories = factories;
		for(RoboRegistration<?> registration : registrations)
		{
			_map.put(registration.getToType(), registration);
		}
	}

	public RoboContainer(Context context, ArrayList<RoboRegistration<?>> registrations, ArrayList<IRoboFactory> factories){
		this(registrations, factories);
		this.context = context;
	}

	public <TType> TType resolve(Class<TType> clazz)
	{
		if(factories != null && factories.size() > 0){
			for(IRoboFactory factory : factories){
				if(factory.canCreate(clazz))
					return (TType) factory.create(clazz, this);
			}
		}
		if(!_map.containsKey(clazz))
		{
			if(!clazz.isInterface() && !clazz.isAnnotation() && !clazz.isSynthetic())
			{
				RoboRegistration<TType> tTypeRoboRegistration = new RoboRegistration<>(clazz);
				tTypeRoboRegistration.toSelf();
				_map.put(clazz, tTypeRoboRegistration);
			}
		}
		TType instance = (TType) _map.get(clazz).getInstance(this);
		inject(instance);
		return instance;
	}

	public void inject(Object obj)
	{
		Class<?> aClass = obj.getClass();
		Field[] fields = aClass.getDeclaredFields();
		for(Field field : fields)
		{
			Inject annotation = field.getAnnotation(Inject.class);
			if(annotation != null)
			{
				try {
					field.setAccessible(true);
					if(field.getType() == Context.class){
						field.set(obj, getContext());
						continue;
					}
					field.set(obj, resolve(field.getType()));
				} catch (IllegalAccessException e) {
					Log.e(this.getClass().getName(), "Could not inject field " + field.getName(), e);
				}
			}
		}
	}

	protected Context getContext()
	{
		return this.context;
	}
}
