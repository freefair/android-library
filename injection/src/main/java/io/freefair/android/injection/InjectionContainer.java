package io.freefair.android.injection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.freefair.android.util.function.Supplier;
import io.freefair.android.util.function.Optional;
import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.injection.exceptions.InjectionException;

public class InjectionContainer extends Injector {

	private static InjectionContainer instance;

	public static InjectionContainer getInstance() {
		if (instance == null) {
			instance = new InjectionContainer();
			new DefaultModule().configure(instance);
		}
		return instance;
	}

	private Map<Class<?>, Supplier<?>> injectionSupplier;
	private Set<InjectionProvider> injectionFactories;

	private InjectionContainer() {
		super(null);
		injectionSupplier = new HashMap<>();
		injectionFactories = new HashSet<>();
	}

	public <IMPL extends IFACE, IFACE> void registerType(Class<IMPL> impl, final Class<IFACE> iFace) {
		this.registerProvider(new TypeRegistration<>(impl, iFace));
	}

	public <T> void registerSupplier(Class<T> type, Supplier<T> supplier) {
		injectionSupplier.put(type, supplier);
	}

	public void registerProvider(InjectionProvider injectionProvider) {
		injectionFactories.add(injectionProvider);
	}

	@Override
	protected void inject(@NonNull Object instance, @NonNull Field field) {
		if (field.isAnnotationPresent(Inject.class)) {
			Inject injectAnnotation = field.getAnnotation(Inject.class);

			Class<?> targetType = injectAnnotation.value().equals(Object.class)
										  ? resolveTargetType(field)
										  : injectAnnotation.value();

			Object value = getInjector(instance).resolveValue(targetType, instance);

			if (value == null) {
				if (!injectAnnotation.optional() && !field.getType().equals(Optional.class)) {
					throw new InjectionException("Unable to resolve value of type " + targetType.toString() + " for Field " + field.toString());
				}
			}

			inject(instance, field, value);
		} else {
			super.inject(instance, field);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {
		if (type.isAssignableFrom(Injector.class)) {
			return (T) this;
		}

		if (type.isAnnotation()) {
			Class<? extends Annotation> annotationType = (Class<? extends Annotation>) type;
			return (T) instance.getClass().getAnnotation(annotationType);
		}

		Optional<T> supplierValue = querySupplier(type);
		if (supplierValue.isPresent())
			return supplierValue.get();

		Optional<T> factoryValue = queryFactories(type, instance);
		if (factoryValue.isPresent())
			return factoryValue.get();

		return super.resolveValue(type, instance);
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> querySupplier(Class<T> type) {

		Supplier<T> supplier = (Supplier<T>) injectionSupplier.get(type);

		if (supplier == null) {
			for (Map.Entry<Class<?>, Supplier<?>> supplierEntry : injectionSupplier.entrySet()) {
				if (type.isAssignableFrom(supplierEntry.getKey())) {
					supplier = (Supplier<T>) supplierEntry.getValue();
				}
			}
		}

		if (supplier != null) {
			T value = supplier.get();
			inject(value);
			return Optional.ofNullable(value);
		}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> queryFactories(Class<T> type, Object instance) {
		for (InjectionProvider factory : injectionFactories) {
			if (factory.canProvide(type))
				return Optional.of(factory.provide(type, instance, this));
		}
		return Optional.empty();
	}
}
