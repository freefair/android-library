package de.larsgrefer.android.library.injection;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class FragmentInjector extends Injector<Fragment> {


	private IViewFinder viewFinder;

	public FragmentInjector(Fragment fragment){
		super(fragment);
	}

	public FragmentInjector(Fragment fragment, Class<?> rClass){
		super(fragment, rClass);
	}

	@Override
	protected View findViewById(@IdRes int viewId) {
		View view = null;
		if( getObject().getView() != null){
			view = getObject().getView().findViewById(viewId);
		}
		else if(viewFinder != null && view == null){
			view = viewFinder.findViewById(viewId);
		}
		return view;
	}

	public void setViewFinder( IViewFinder viewFinder ) {
		this.viewFinder = viewFinder;
	}
}
