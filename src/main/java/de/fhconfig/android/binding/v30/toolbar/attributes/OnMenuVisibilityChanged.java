package de.fhconfig.android.binding.v30.toolbar.attributes;

import android.support.v7.app.ActionBar.OnMenuVisibilityListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.actionbar.listeners.OnMenuVisibilityListenerMulticast;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnMenuVisibilityChanged
		extends ViewEventAttribute<BindableToolbar>
		implements OnMenuVisibilityListener {

	public OnMenuVisibilityChanged(BindableToolbar view) {
		super(view, "onMenuVisibilityChanged");
	}

	@Override
	protected void registerToListener(BindableToolbar view) {
		Binder.getMulticastListenerForView(view, OnMenuVisibilityListenerMulticast.class)
				.register(this);
	}

	public void onMenuVisibilityChanged(boolean isVisible) {
		this.invokeCommand(this.getView(), isVisible);
	}
}