package de.larsgrefer.android.library.ui.injection;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import de.larsgrefer.android.library.injection.ActivityXmlInjector;
import de.larsgrefer.android.library.injection.exceptions.ViewIdNotFoundException;

public class InjectionActivity extends ActionBarActivity {
	ActivityXmlInjector injector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injector = new ActivityXmlInjector(this);
		injector.tryInjectLayout();

		injector.injectResources();
		injector.injectAttributes();

		Application app = getApplication();
		if (app instanceof InjectionApplication)
			((InjectionApplication) app).getInjector().inject(this);
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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		injector.injectResources();
		injector.injectAttributes();
	}

	@Override
	public void setTheme(int resid) {
		super.setTheme(resid);
		if (injector != null) {
			injector.injectResources();
			injector.injectAttributes();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (injector.getMenuId() != 0) {
			getMenuInflater().inflate(injector.getMenuId(), menu);
			super.onCreateOptionsMenu(menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
}
