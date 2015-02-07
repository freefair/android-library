package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnItemClickListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * Fires when Item Clicked
 * Equals to listening to AdapterView.OnItemClickListener.onItemClick
 *
 * @author andy
 * @name onItemClicked
 * @widget ListView
 * @type Command
 * @accepts Command
 * @category list
 * @related
 */
public class OnItemClickedViewEvent extends ViewEventAttribute<AdapterView<?>>
		implements AdapterView.OnItemClickListener {

	public OnItemClickedViewEvent(AdapterView<?> view) {
		super(view, "onItemClicked");
	}

	@Override
	protected void registerToListener(AdapterView<?> view) {
		Binder.getMulticastListenerForView(view, OnItemClickListenerMulticast.class).register(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		this.invokeCommand(arg0, arg1, arg2, arg3);
	}
}
