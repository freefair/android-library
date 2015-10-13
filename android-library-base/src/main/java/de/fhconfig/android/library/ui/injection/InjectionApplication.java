package de.fhconfig.android.library.ui.injection;

import android.app.Application;

import de.fhconfig.android.library.Logger;
import de.fhconfig.android.library.injection.InjectionContainer;
import de.fhconfig.android.library.injection.InjectionProvider;
import de.fhconfig.android.library.injection.Injector;
import de.fhconfig.android.library.injection.InjectorProvider;
import de.fhconfig.android.library.util.Suppliers;

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
