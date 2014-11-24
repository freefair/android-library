package de.larsgrefer.android.library.injection;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by larsgrefer on 24.11.14.
 */
public interface IViewFinder {
	public View findViewById(@IdRes int viewId);
}
