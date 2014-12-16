package de.fhconfig.android.binding.viewAttributes.view;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnTouchListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchViewEvent extends ViewEventAttribute<View> implements View.OnTouchListener {
	public OnTouchViewEvent(View view) {
		super(view, "onTouch");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		TouchEventResult result = new TouchEventResult();
		this.invokeCommand(v, event, result);
		return result.eventConsumed;
	}
	
	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnTouchListenerMulticast.class).register(this);
	}
}
