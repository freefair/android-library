package de.larsgrefer.android.library.ui;

import android.support.v7.app.ActionBarActivity;

import de.larsgrefer.android.library.injection.Injector;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class InjectionActionBarActivity extends ActionBarActivity {

	public void setContentView(int layoutResID, Class<?> rIdClass) {
		super.setContentView(layoutResID);
		Injector.injectViews(this, rIdClass);
	}
}
