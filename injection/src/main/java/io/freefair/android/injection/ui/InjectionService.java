package io.freefair.android.injection.ui;

import android.app.Service;
import android.content.Intent;

import io.freefair.android.injection.InjectionContainer;
import io.freefair.android.injection.Injector;
import io.freefair.android.injection.InjectorProvider;

/**
 * A {@link Service} with support for dependency injection
 */
public abstract class InjectionService extends Service implements InjectorProvider {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Injector injector = getInjector();
		if(injector != null)
			injector.inject(this);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public Injector getInjector() {
		return InjectionContainer.getInstance();
	}
}
