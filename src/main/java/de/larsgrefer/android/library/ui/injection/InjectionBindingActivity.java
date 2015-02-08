package de.larsgrefer.android.library.ui.injection;

import android.app.Application;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import de.larsgrefer.android.library.injection.ActivityXmlInjector;
import de.larsgrefer.android.library.injection.exceptions.ViewIdNotFoundException;
import de.fhconfig.android.binding.v30.app.BindingActivityV30;

public class InjectionBindingActivity extends BindingActivityV30 {
	ActivityXmlInjector injector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injector = new ActivityXmlInjector(this);
		injector.tryInjectLayout();

		Application app = getApplication();
		if(app instanceof InjectionApplication)
			((InjectionApplication)app).getInjector().inject(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		tryInjectViews();
	}

	private void tryInjectViews() {
		try {
			injector.injectViews();
		} catch (ViewIdNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		tryInjectViews();
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
		tryInjectViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(injector.getMenuId() != 0){
			getMenuInflater().inflate(injector.getMenuId(), menu);
			super.onCreateOptionsMenu(menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
}
