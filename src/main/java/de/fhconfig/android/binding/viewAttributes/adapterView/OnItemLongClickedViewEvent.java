package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnItemLongClickListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * Fires when Item Long-Clicked
 * Equals to listening to AdapterView.OnItemLongClickListener.onItemLongClick()
 *
 * @author andy
 *         <p/>
 *         User: =ra=
 *         Date: 28.07.11
 *         Time: 17:27
 * @name onItemLongClicked
 * @widget ListView
 * @type Command
 * @accepts Command
 * @category list
 * @related
 */
public class OnItemLongClickedViewEvent extends ViewEventAttribute<AdapterView<?>>
		implements AdapterView.OnItemLongClickListener {

	public OnItemLongClickedViewEvent(AdapterView<?> view) {
		super(view, "onItemLongClicked");
	}

	@Override
	protected void registerToListener(AdapterView<?> view) {
		Binder.getMulticastListenerForView(view, OnItemLongClickListenerMulticast.class).register(this);
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		this.invokeCommand(parent, view, position, id);
		return true;
	}
}
