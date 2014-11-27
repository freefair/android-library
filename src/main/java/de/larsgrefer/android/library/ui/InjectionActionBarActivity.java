package de.larsgrefer.android.library.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import de.larsgrefer.android.library.injection.ActivityXmlInjector;
import de.larsgrefer.android.library.injection.ViewIdNotFoundException;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class InjectionActionBarActivity extends ActionBarActivity {

	ActivityXmlInjector injector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injector = new ActivityXmlInjector(this);
		injector.tryInjectLayout();
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
