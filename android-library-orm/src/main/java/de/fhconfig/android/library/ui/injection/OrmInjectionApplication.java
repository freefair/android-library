package de.fhconfig.android.library.ui.injection;

import java.util.List;

import de.fhconfig.android.library.data.OpenHelper;

public class OrmInjectionApplication extends InjectionApplication
{
	@Override
	public void onCreate() {
		addModule(new MainModule());
		super.onCreate();
	}

	protected void enableOrm(List<Class<?>> objects, int version) {
		new DatabaseHelper(new OpenHelper(this, version)).registerObjects(objects);
	}
}
