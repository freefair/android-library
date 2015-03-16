package de.fhconfig.android.library.injection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class RoboRegistration<TType>
{
	private Class<TType> type;

	private RoboRegistrationExtended<TType, ?> extended;

	private TType instance = null;

	public RoboRegistration(Class<TType> type)
	{
		this.type = type;
	}

	public <TIType> RoboRegistrationExtended<TType, TIType> to(Class<TIType> type)
	{
		return (RoboRegistrationExtended<TType, TIType>) (extended = new RoboRegistrationExtended<>(this.type, type));
	}

	public RoboRegistrationExtended<TType, TType> toSelf()
	{
		return (RoboRegistrationExtended<TType, TType>) (extended = new RoboRegistrationExtended<TType, TType>(this.type, this.type));
	}

	protected Class<?> getToType() {
		return extended.getType();
	}

	private TType create(RoboContainer container)
	{
		try
		{
			Constructor<?>[] constructors = type.getConstructors();
			Constructor<?> constructor = null;
			for(Constructor<?> con : constructors)
			{
				if(constructor == null)
					constructor = con;
				else
				{
					if(constructor.getParameterTypes().length > con.getParameterTypes().length)
						constructor = con;
				}
			}
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			List<Object> objectList = new ArrayList<>();
			for(Class<?> cls : parameterTypes)
			{
				objectList.add(container.resolve(cls));
			}
			return (TType)constructor.newInstance(objectList.toArray());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Class " + type.toString() + " not instanciable");
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Class " + type.toString() + " not instanciable", e);
		}
	}

	protected TType getInstance(RoboContainer container) {
		if(extended.isSingleton())
		{
			if(instance == null)
				instance = create(container);
			return instance;
		}
		return create(container);
	}
}
