package io.freefair.android.injection.ui;

import android.app.Application;

import io.freefair.android.injection.InjectionContainer;
import io.freefair.android.util.Suppliers;

/**
 * An {@link Application} with support for dependency injection
 */
public class InjectionApplication extends Application {
	private InjectionContainer injector;

	public InjectionApplication() {
		injector = InjectionContainer.getInstance();
		injector.registerSupplier(InjectionApplication.class, Suppliers.of(this));
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
