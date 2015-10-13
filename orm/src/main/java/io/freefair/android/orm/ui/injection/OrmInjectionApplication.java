package io.freefair.android.orm.ui.injection;

import java.util.List;

import io.freefair.android.orm.OpenHelper;
import io.freefair.android.ui.injection.InjectionApplication;

public class OrmInjectionApplication extends InjectionApplication
{
	@Override
	public void onCreate() {
		new MainModule().configure(getInjector());
		super.onCreate();
	}

	protected void enableOrm(List<Class<?>> objects, int version) {
		new DatabaseHelper(new OpenHelper(this, version)).registerObjects(objects);
	}
}
