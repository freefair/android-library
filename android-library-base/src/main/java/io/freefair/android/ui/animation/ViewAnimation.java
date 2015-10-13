package io.freefair.android.ui.animation;

import android.view.View;

/**
 * Created by larsgrefer on 02.02.15.
 */
public abstract class ViewAnimation<T extends View> extends MultiListenerAnimation {

	protected T view;

	public ViewAnimation() {
		super();
	}

	public ViewAnimation(T view) {
		super();
		this.view = view;
	}

	public void setView(T view) {
		this.view = view;
	}

	public T getView() {
		return view;
	}
}
