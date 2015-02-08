package de.larsgrefer.android.library.ui.injection;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.DefaultKernelV30;

public class InjectionBindingApplication extends InjectionApplication
{
	@Override
	public void onCreate() {
		super.onCreate();
		Binder.init(this, new DefaultKernelV30());
	}
}
