package de.fhconfig.android.binding.v30.actionbar.attributes;

import android.support.v7.app.ActionBar.OnMenuVisibilityListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;
import de.fhconfig.android.binding.v30.actionbar.listeners.OnMenuVisibilityListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnMenuVisibilityChanged
		extends ViewEventAttribute<BindableActionBar>
		implements OnMenuVisibilityListener {

	public OnMenuVisibilityChanged(BindableActionBar view) {
		super(view, "onMenuVisibilityChanged");
	}

	@Override
	protected void registerToListener(BindableActionBar view) {
		Binder.getMulticastListenerForView(view, OnMenuVisibilityListenerMulticast.class)
				.register(this);
	}

	public void onMenuVisibilityChanged(boolean isVisible) {
		this.invokeCommand(this.getView(), isVisible);
	}
}