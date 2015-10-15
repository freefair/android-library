package io.freefair.android.injection.platform;

import android.support.annotation.IdRes;
import android.view.View;

public interface IViewFinder {
	public View findViewById(@IdRes int viewId);
}
