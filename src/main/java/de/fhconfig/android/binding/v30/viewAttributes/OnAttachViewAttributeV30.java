package de.fhconfig.android.binding.v30.viewAttributes;

import android.annotation.TargetApi;
import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.listeners.OnAttachStateChangeListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

import static android.os.Build.VERSION_CODES.HONEYCOMB_MR1;

@TargetApi(HONEYCOMB_MR1)
public class OnAttachViewAttributeV30 extends ViewEventAttribute<View> implements View.OnAttachStateChangeListener {
	public OnAttachViewAttributeV30(View view) {
		super(view, "onAttach");
	}

	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnAttachStateChangeListenerMulticast.class).register(this);
	}

	public void onViewAttachedToWindow(View v) {
		this.invokeCommand(v);
	}

	public void onViewDetachedFromWindow(View v) {
	}

}

