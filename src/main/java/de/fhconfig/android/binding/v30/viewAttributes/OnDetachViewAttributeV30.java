package de.fhconfig.android.binding.v30.viewAttributes;

import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.listeners.OnAttachStateChangeListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnDetachViewAttributeV30 extends ViewEventAttribute<View> implements View.OnAttachStateChangeListener {
	public OnDetachViewAttributeV30(View view) {
		super(view, "onDetach");
	}

	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnAttachStateChangeListenerMulticast.class).register(this);
	}

	public void onViewAttachedToWindow(View v) {
	}

	public void onViewDetachedFromWindow(View v) {
		this.invokeCommand(v);
	}

}

