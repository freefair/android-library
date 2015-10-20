package io.freefair.android.orm.ui.injection;

import java.util.List;

import io.freefair.android.injection.InjectionContainer;
import io.freefair.android.injection.ui.InjectionApplication;
import io.freefair.android.orm.OpenHelper;

public class OrmInjectionApplication extends InjectionApplication
{
	@Override
	public void onCreate() {
		new MainModule().configure(InjectionContainer.getInstance());
		super.onCreate();
	}

	protected void enableOrm(List<Class<?>> objects, int version) {
		new DatabaseHelper(new OpenHelper(this, version)).registerObjects(objects);
	}
}
