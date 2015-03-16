package de.fhconfig.android.library.injection.xml;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

import de.fhconfig.android.library.injection.helper.RClassHelper;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class FragmentXmlInjector extends XmlInjector<Fragment> {

	private IViewFinder viewFinder;

	public FragmentXmlInjector(Fragment fragment){
		this(fragment, RClassHelper.getRClassFromFragment(fragment));
	}

	public FragmentXmlInjector(Fragment fragment, Class<?> rClass){
		super(fragment, rClass);
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
