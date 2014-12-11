package de.larsgrefer.android.library.ui;

import gueei.binding.Binder;
import gueei.binding.v30.DefaultKernelV30;

public class InjectionBindingApplication extends InjectionApplication
{
	@Override
	public void onCreate() {
		super.onCreate();
		Binder.init(this, new DefaultKernelV30());
	}
}
