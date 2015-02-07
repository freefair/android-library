package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnFocusChangeListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnGainFocusViewEvent extends ViewEventAttribute<View> implements View.OnFocusChangeListener {
	public OnGainFocusViewEvent(View view) {
		super(view, "onGainFocus");
	}

	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnFocusChangeListenerMulticast.class).register(this);
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus)
			invokeCommand(v);
	}
}