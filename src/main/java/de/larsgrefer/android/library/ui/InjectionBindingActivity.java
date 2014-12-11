package de.larsgrefer.android.library.ui;

import android.app.Application;
import android.os.Bundle;
import gueei.binding.v30.app.BindingActivityV30;

public class InjectionBindingActivity extends BindingActivityV30 {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Application app = getApplication();
		if(app instanceof InjectionApplication)
			((InjectionApplication)app).getInjector().inject(this);
	}
}
