package de.larsgrefer.android.library.injection;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class ActivityInjector extends Injector<Activity> {



	public ActivityInjector(Activity activity){
		super(activity);
	}

	public ActivityInjector(Activity activity, Class<?> rClass){
		super(activity, rClass);
	}

	public boolean tryInjectLayout(){
		if(getLayoutId() != 0){
			getObject().setContentView(getLayoutId());
			return true;
		}
		return false;
	}

	@Override
	protected View findViewById(@IdRes int id) {
		return getObject().findViewById(id);
	}
}
