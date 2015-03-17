package de.fhconfig.android.library.injection.xml;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.library.injection.helper.RClassHelper;

/**
 * Created by Dennis Fricke on 28.01.2015.
 */
public class ViewGroupXmlInjector extends XmlInjector<ViewGroup> {
    public ViewGroupXmlInjector(ViewGroup object) {
        super(object, RClassHelper.getRClassFromViewGroup(object));
    }

	@Override
	protected Context getContext() {
		return getObject().getContext();
	}

	@Override
	protected Resources.Theme getTheme() {
		return getObject().getContext().getTheme();
	}

	@Override
    protected View findViewById(@IdRes int viewId) {
        return getObject().findViewById(viewId);
    }
}
