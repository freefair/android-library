package io.freefair.android.orm.ui.injection;

import java.util.List;

import io.freefair.android.injection.app.InjectionApplication;
import io.freefair.android.orm.OpenHelper;

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
