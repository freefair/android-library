package de.larsgrefer.android.library.ui.injection;

import android.app.Application;
import de.larsgrefer.android.library.data.OpenHelper;
import de.larsgrefer.android.library.injection.RoboBuilder;
import de.larsgrefer.android.library.injection.RoboContainer;
import de.larsgrefer.android.library.injection.RoboModule;

import java.util.List;

public class InjectionApplication extends Application
{
	private RoboBuilder builder = new RoboBuilder();
	private RoboContainer injector;

	protected void addModule(RoboModule module)
	{
		builder.registerModule(module);
	}

	@Override
	public void onCreate() {
		builder.registerModule(new MainModule());
		super.onCreate();
		injector = builder.build();
	}

	protected RoboContainer getInjector()
	{
		return injector;
	}

	protected void enableOrm(List<Class<?>> objects, int version)
	{
		new DatabaseHelper(new OpenHelper(this, version)).registerObjects(objects);
	}
}
