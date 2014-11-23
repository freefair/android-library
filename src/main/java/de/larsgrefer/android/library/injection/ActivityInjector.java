package de.larsgrefer.android.library.injection;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class ActivityInjector<T extends Activity> extends Injector {

	T activity;
	Class<T> activityClass;

	public ActivityInjector(T activity){
		this.activity = activity;
		this.activityClass = (Class<T>) activity.getClass();
	}

	@Override
	protected View findViewId(Field field) {
		return null;
	}
}
