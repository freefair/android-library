package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnClickListenerMulticast;
import de.fhconfig.android.binding.utility.EventMarkerHelper;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;
import de.fhconfig.android.binding.viewAttributes.adapterView.listView.ItemViewEventMark;

public class OnClickViewEvent extends ViewEventAttribute<View> implements View.OnClickListener {
	public OnClickViewEvent(View view) {
		super(view, "onClick");
	}

	public void onClick(View v) {
		invokeCommand(v);
	}

	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnClickListenerMulticast.class).register(this);
		ItemViewEventMark mark = EventMarkerHelper.getMark(view);
		if (null != mark) {
			Binder.getMulticastListenerForView(view, OnClickListenerMulticast.class).registerWithHighPriority(mark);
		}
	}
}
