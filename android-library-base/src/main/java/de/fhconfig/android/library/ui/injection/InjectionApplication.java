package de.fhconfig.android.library.ui.injection;

import android.app.Application;

import de.fhconfig.android.library.injection.RoboBuilder;
import de.fhconfig.android.library.injection.RoboContainer;
import de.fhconfig.android.library.injection.RoboModule;

public class InjectionApplication extends Application
{
	private RoboBuilder builder = new RoboBuilder();
	private RoboContainer injector;

	protected void addModule(RoboModule module) {
		builder.registerModule(module);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		injector = builder.build(this);
	}

	public RoboContainer getInjector()
	{
		return injector;
	}

}
