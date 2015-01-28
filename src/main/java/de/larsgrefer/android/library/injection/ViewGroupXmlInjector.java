package de.larsgrefer.android.library.injection;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

import de.larsgrefer.android.library.injection.helper.RClassHelper;

/**
 * Created by Dennis Fricke on 28.01.2015.
 */
public class ViewGroupXmlInjector extends XmlInjector<ViewGroup> {
    public ViewGroupXmlInjector(ViewGroup object) {
        super(object, RClassHelper.getRClassFromViewGroup(object));
    }

    @Override
    protected View findViewById(@IdRes int viewId) {
        return getObject().findViewById(viewId);
    }
}
