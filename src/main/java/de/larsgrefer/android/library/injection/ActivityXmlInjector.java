package de.larsgrefer.android.library.injection;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class ActivityXmlInjector extends XmlInjector<Activity> {

	public ActivityXmlInjector(Activity activity){
		super(activity, RClassHelper.getRClassFromActivity(activity));
	}

	public ActivityXmlInjector(Activity activity, Class<?> rClass){
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
