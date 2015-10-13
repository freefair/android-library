package io.freefair.android.injection.ui;

import android.app.Application;

import io.freefair.android.util.Logger;
import io.freefair.android.injection.InjectionContainer;
import io.freefair.android.injection.InjectionProvider;
import io.freefair.android.injection.Injector;
import io.freefair.android.injection.InjectorProvider;
import io.freefair.android.util.Suppliers;

/**
 * An {@link Application} with support for dependency injection
 */
public class InjectionApplication extends Application implements InjectorProvider {
	private InjectionContainer injector;

	public InjectionApplication() {
		injector = new InjectionContainer(null);
		injector.registerSupplier(InjectionApplication.class, Suppliers.of(this));
		injector.registerProvider(new InjectionProvider() {
			@Override
			public boolean canProvide(Class<?> clazz) {
				return clazz.isAssignableFrom(Logger.class);
			}

			@Override
			@SuppressWarnings("unchecked")
			public <T> T provide(Class<T> clazz, Object instance, Injector injector) {
				return (T) Logger.forObject(instance);
			}
		});
	}

	@Override
	public void onCreate() {
		super.onCreate();
		injector.inject(this);
	}

	public InjectionContainer getInjector() {
		return injector;
	}

}
