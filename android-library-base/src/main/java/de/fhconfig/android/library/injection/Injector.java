package de.fhconfig.android.library.injection;

import java.lang.reflect.Field;

import de.fhconfig.android.library.reflection.Reflection;
import de.fhconfig.android.library.util.Optional;

/**
 * Created by larsgrefer on 11.10.15.
 */
public abstract class Injector {

	private Optional<Injector> parentInjector;

	protected abstract <T> T getObject(Class<T> clazz);

	public void inject(Object instance){
		inject(instance, instance.getClass());
	}

	public void inject(Object instance, Class<?> clazz){
		for (Field field : Reflection.getAllFields(clazz, Object.class)) {
			inject(instance, field);
		}
	}

	protected abstract void inject(Object instance, Field field);
}
