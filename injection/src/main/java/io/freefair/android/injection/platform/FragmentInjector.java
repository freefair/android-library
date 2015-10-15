package io.freefair.android.injection.platform;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.helper.RClassHelper;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class FragmentInjector extends AndroidInjector<Fragment> {

	private IViewFinder viewFinder;

	public FragmentInjector(Fragment fragment, Injector parentInjector){
		super(parentInjector, fragment, RClassHelper.getRClassFromFragment(fragment));
	}

	@Override
	protected View findViewById(@IdRes int viewId) {

		if( getObject().getView() != null){
			return getObject().getView().findViewById(viewId);
		}
		else if(viewFinder != null){
			return viewFinder.findViewById(viewId);
		}
		return null;
	}

	public void setViewFinder( IViewFinder viewFinder ) {
		this.viewFinder = viewFinder;
	}

}
