package de.larsgrefer.android.library.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import de.larsgrefer.android.library.injection.ActivityInjector;
import de.larsgrefer.android.library.injection.Injector;
import de.larsgrefer.android.library.injection.ViewIdNotFoundException;
import de.larsgrefer.android.library.injection.annotation.XmlLayout;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class InjectionActionBarActivity extends ActionBarActivity {

	ActivityInjector injector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injector = new ActivityInjector(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		try {
			injector.injectViews();
		} catch (ViewIdNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
