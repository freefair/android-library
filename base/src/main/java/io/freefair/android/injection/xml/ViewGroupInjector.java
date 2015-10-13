package io.freefair.android.injection.xml;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.helper.RClassHelper;

/**
 * Created by Dennis Fricke on 28.01.2015.
 */
public class ViewGroupInjector extends AndroidInjector<ViewGroup> {
	public ViewGroupInjector(ViewGroup object, Injector parentInjector) {
		super(parentInjector, object, RClassHelper.getRClassFromViewGroup(object));
	}

	@Override
	protected View findViewById(@IdRes int viewId) {
		return getObject().findViewById(viewId);
	}
}
