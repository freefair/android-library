package io.freefair.android.injection.ui;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.InjectorProvider;
import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.injection.annotation.XmlLayout;
import io.freefair.android.injection.annotation.XmlMenu;
import io.freefair.android.injection.xml.ActivityInjector;
import io.freefair.android.util.Optional;


/**
 * An {@link AppCompatActivity} with support for dependency injection
 */
public class InjectionAppCompatActivity extends AppCompatActivity implements InjectorProvider {
	ActivityInjector injector;

	@Inject private Optional<XmlMenu> xmlMenuAnnotation;
	@Inject private Optional<XmlLayout> xmlLayoutAnnotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector parentInjector = null;
		if(getApplication() instanceof InjectorProvider){
			parentInjector = ((InjectorProvider)getApplication()).getInjector();
		}
		injector = new ActivityInjector(this, parentInjector);
		injector.inject(this);

		if(xmlLayoutAnnotation.isPresent()){
			setContentView(xmlLayoutAnnotation.get().value());
		}

		injector.injectResources();
		injector.injectAttributes();
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		tryInjectViews();
	}

	private void tryInjectViews() {
		injector.injectViews();
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
		if (xmlMenuAnnotation.isPresent()) {
			getMenuInflater().inflate(xmlMenuAnnotation.get().value(), menu);
			super.onCreateOptionsMenu(menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public ActivityInjector getInjector() {
		return injector;
	}
}
