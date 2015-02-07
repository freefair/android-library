package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnLongClickListenerMulticast;
import de.fhconfig.android.binding.utility.EventMarkerHelper;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;
import de.fhconfig.android.binding.viewAttributes.adapterView.listView.ItemViewEventMark;

public class OnLongClickViewEvent extends ViewEventAttribute<View> implements View.OnLongClickListener {

	public OnLongClickViewEvent(View view) {
		super(view, "onLongClick");
	}

	public boolean onLongClick(View v) {
		this.invokeCommand(v);
		return true;
	}

	@Override
	protected void registerToListener(View view) {
		Binder.getMulticastListenerForView(view, OnLongClickListenerMulticast.class).register(this);
		ItemViewEventMark mark = EventMarkerHelper.getMark(view);
		if (null != mark) {
			Binder.getMulticastListenerForView(view, OnLongClickListenerMulticast.class).registerWithHighPriority(mark);
		}
	}
}
