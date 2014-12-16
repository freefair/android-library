package de.fhconfig.android.binding.viewAttributes.view;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnKeyListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;
import android.view.KeyEvent;
import android.view.View;

public class OnKeyViewEvent extends ViewEventAttribute<View> implements View.OnKeyListener {
	public OnKeyViewEvent(View view) {
		super(view, "onKey");
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		KeyEventResult result = new KeyEventResult();			
		this.invokeCommand(v, keyCode, event, result);		
		return result.eventConsumed;
	}
	
	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnKeyListenerMulticast.class).register(this);
	}

}
