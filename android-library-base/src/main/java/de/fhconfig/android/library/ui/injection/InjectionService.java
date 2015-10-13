package de.fhconfig.android.library.ui.injection;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import de.fhconfig.android.library.injection.Injector;
import de.fhconfig.android.library.injection.InjectorProvider;

/**
 * A {@link Service} with support for dependency injection
 */
public class InjectionService extends Service implements InjectorProvider {
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Injector injector = getInjector();
		if(injector != null)
			injector.inject(this);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public Injector getInjector() {
		Application application = getApplication();
		if(application instanceof InjectorProvider){
			return ((InjectorProvider)application).getInjector();
		}
		return null;
	}
}
