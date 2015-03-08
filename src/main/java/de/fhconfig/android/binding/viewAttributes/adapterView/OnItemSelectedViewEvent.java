package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnItemSelectedListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * Fires when Item Selected
 * Equals to listening to AdapterView.OnItemSelectedListener
 *
 * @author andy
 * @name onItemSelected
 * @widget ListView
 * @type Command
 * @accepts Command
 * @category list
 * @related
 */
public class OnItemSelectedViewEvent extends ViewEventAttribute<AdapterView<?>>
		implements AdapterView.OnItemSelectedListener {

	public OnItemSelectedViewEvent(AdapterView<?> view) {
		super(view, "onItemSelected");
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	                           long arg3) {
		this.invokeCommand(arg0, arg1, arg2, arg3);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		this.invokeCommand(arg0);
	}

	@Override
	protected void registerToListener(AdapterView<?> view) {
		Binder.getMulticastListenerForView(view, OnItemSelectedListenerMulticast.class).register(this);
	}
}
