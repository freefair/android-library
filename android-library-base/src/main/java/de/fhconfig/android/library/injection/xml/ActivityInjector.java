package de.fhconfig.android.library.injection.xml;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import de.fhconfig.android.library.injection.Injector;
import de.fhconfig.android.library.injection.helper.RClassHelper;

public class ActivityInjector extends AndroidInjector<Activity> {

	public ActivityInjector(Activity activity, Injector parentInjector){
		super(parentInjector, activity, RClassHelper.getRClassFromActivity(activity));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T resolveValue(@NonNull Class<T> type, Object instance) {
		if(type.isAssignableFrom(Resources.Theme.class)){
			return (T) getObject().getTheme();
		}
		return super.resolveValue(type, instance);
	}

	public boolean tryInjectLayout(){
		int layoutId = getLayoutId();
		if(layoutId != 0){
			getObject().setContentView(layoutId);
			return true;
		}
		return false;
	}

	@Override
	protected View findViewById(@IdRes int id) {
		return getObject().findViewById(id);
	}

}
