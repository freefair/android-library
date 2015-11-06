package io.freefair.android.injection;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;

import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.injection.exceptions.InjectionException;
import io.freefair.android.injection.reflection.Reflection;
import io.freefair.android.util.Optional;
import io.freefair.android.util.log.Logger;
import io.freefair.android.util.log.Loggers;

/**
 * Abstact implementation of a dependency injector
 */
public abstract class Injector {

	private final Logger log = Loggers.forClass(Injector.class);
	private Optional<Injector> parentInjector;

	public Injector(@Nullable Injector parentInjector) {
		this.parentInjector = Optional.ofNullable(parentInjector);
		topClasses = new HashSet<>();
		topClasses.add(Activity.class);
		topClasses.add(Fragment.class);
		topClasses.add(Application.class);
	}

	private WeakHashMap<Object, Class<?>> alreadyInjectedInstances = new WeakHashMap<>();
	private static WeakHashMap<Object, Injector> responsibleInjectors = new WeakHashMap<>();

	protected Injector getInjector(Object instance){
		Injector injector = responsibleInjectors.get(instance);
		if(injector != null)
			return injector;
		return this;
	}

	/**
	 * Injects as much as possible into the given object
	 *
	 * @param instance The object to inject into
	 */
	public final void inject(@NonNull Object instance) {
		inject(instance, instance.getClass());
	}

	Deque<Object> instancesStack = new LinkedList<>();

	public final void inject(@NonNull Object instance, @NonNull Class<?> clazz) {
		responsibleInjectors.put(instance, this);
		if (!alreadyInjectedInstances.containsKey(instance)) {
			long start = System.currentTimeMillis();

			instancesStack.addLast(instance);
			alreadyInjectedInstances.put(instance, clazz);
			for (Field field : Reflection.getAllFields(clazz, getUpToExcluding(clazz))) {
				inject(instance, field);
			}
			instancesStack.removeLast();

			long end = System.currentTimeMillis();
			log.debug("Injection of " + instance + " took " + (end - start) + "ms");
		}
	}

	private Set<Class<?>> topClasses;

	@NonNull
	@SuppressWarnings("unchecked")
	private <X> Class<X> getUpToExcluding(Class<? extends X> clazz) {
		for (Class<?> topClazz : topClasses) {
			if(topClazz.isAssignableFrom(clazz))
				return (Class<X>) topClazz;
		}
		return (Class<X>) Object.class;
	}

	/**
	 * Inject the field, or call super
	 *
	 * @param instance the instance to inject into
	 * @param field    the field to inject
	 */
	protected void inject(@NonNull Object instance, @NonNull Field field) {
		if (parentInjector.isPresent()) {
			parentInjector.get().inject(instance, field);
		}
	}

	/**
	 * Resolve the given type to an object, or call super
	 * <p/>
	 * The base implementation asks the parent if possible or tries to provide a new instance
	 *
	 * @param type     the type of the object to return
	 * @param instance the instance the returned object will be injected into
	 * @param <T>      the type of the object to return
	 * @return The object to use for the given type
	 */
	@Nullable
	public <T> T resolveValue(@NonNull Class<T> type, @Nullable Object instance) {
		for (Object inst : instancesStack) {
			if(type.isInstance(inst))
				return (T) inst;
		}

		if (parentInjector.isPresent()) {
			return parentInjector.get().resolveValue(type, instance);
		} else {
			try {
				return createNewInstance(type, instance);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Nullable
	@SuppressWarnings("unchecked")
	private <T> T createNewInstance(@NonNull Class<T> type, Object instance) {

		T newInstance = null;
		try {
			newInstance = type.newInstance();
		} catch (Exception e) {
			//Look for constructor annotated with @Inject
			for (Constructor<?> constructor : type.getConstructors()) {
				if (constructor.isAnnotationPresent(Inject.class)) {

					//resolve constructor params;
					Class<?>[] parameterTypes = constructor.getParameterTypes();
					Object[] parameterValues = new Object[parameterTypes.length];
					for (int i = 0; i < parameterTypes.length; i++) {
						parameterValues[i] = getInjector(instance).resolveValue(parameterTypes[i], null);
					}

					try {
						newInstance = (T) constructor.newInstance(parameterValues);
					} catch (Exception e1) {
						log.error("Error while calling constructor " + constructor.toString(), e1);
					}
				}
			}
		}
		if(newInstance != null) {
			getInjector(instance).inject(newInstance);
		}
		return newInstance;
	}

	/**
	 * resolves the actual type needed for the given field
	 * <p/>
	 * This method looks into {@link Optional}s and {@link WeakReference}s
	 * in order to resolve the real target type
	 *
	 * @return The type of the object, which is needed for the given field
	 */
	@NonNull
	protected Class<?> resolveTargetType(@NonNull Field field) {
		if (field.getType().equals(Optional.class))
			return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

		if (field.getType().equals(WeakReference.class))
			return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

		return field.getType();
	}

	/**
	 * Inject the given value into the given field in the given object.
	 * <p/>
	 * This method wraps the value into an {@link Optional} or {@link WeakReference}
	 * if necessary
	 *
	 * @param instance the instance to inject into
	 * @param field    the field to inject into
	 * @param value    the value to inject
	 */
	protected void inject(@NonNull Object instance, @NonNull Field field, @Nullable Object value) {
		field.setAccessible(true);

		if (field.getType().equals(Optional.class))
			value = Optional.ofNullable(value);

		if (field.getType().equals(WeakReference.class))
			value = new WeakReference<>(value);

		try {
			field.set(instance, value);
		} catch (IllegalAccessException e) {
			log.error("Cannot inject value", e);
			throw new InjectionException(e);
		}
	}
}
