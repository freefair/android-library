package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnItemLongClickListenerMulticast;

/**
 * Long-Clicked Item in ListView
 * Changes at ListView.OnItemLongClickListener.onItemLongClick()
 *
 * @author andy
 * @name longClickedItem
 * @widget AdapterView
 * @type Object
 * @accepts Object
 * @category list
 * @related
 */
public class LongClickedItemViewAttribute extends ViewAttribute<AdapterView<?>, Object>
		implements OnItemLongClickListener {

	private Object value;

	public LongClickedItemViewAttribute(AdapterView<?> view, String attributeName) {
		super(Object.class, view, attributeName);
		this.setReadonly(true);
		Binder.getMulticastListenerForView(view, OnItemLongClickListenerMulticast.class)
				.registerWithHighPriority(this);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		// do nothing. this is a readonly attribute
	}

	@Override
	public Object get() {
		return value;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
	                               long id) {
		if (!getView().equals(parent)) return false;
		try {
			this.value = getView().getItemAtPosition(position);
			this.notifyChanged();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
