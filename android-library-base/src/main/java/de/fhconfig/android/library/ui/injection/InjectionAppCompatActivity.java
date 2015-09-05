package de.fhconfig.android.library.ui.injection;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.library.injection.annotation.InjectAnnotation;
import de.fhconfig.android.library.injection.annotation.XmlMenu;
import de.fhconfig.android.library.injection.xml.ActivityXmlInjector;
import de.fhconfig.android.library.injection.exceptions.ViewIdNotFoundException;
import java8.util.Optional;

public class InjectionAppCompatActivity extends AppCompatActivity {
	ActivityXmlInjector injector;

	@InjectAnnotation
	private Optional<XmlMenu> xmlMenuAnnotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injector = new ActivityXmlInjector(this);
		injector.injectAnnotations();

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
}
