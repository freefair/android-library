package de.fhconfig.android.binding.viewAttributes.adapterView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnItemSelectedListenerMulticast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * The Selected Item of Adapter View
 * similar to AdapterView.getSelectedItem()
 * 
 * @name selectedItem
 * @widget ListView
 * @type Object
 * @accepts	Object
 * @category list
 * @related 
 * 
 * @author andy
 */
public class SelectedItemViewAttribute extends ViewAttribute<AdapterView<?>, Object>
	implements OnItemSelectedListener{

	public SelectedItemViewAttribute(AdapterView<?> view, String attributeName) {
		super(Object.class, view, attributeName);
		this.setReadonly(true);
		Binder.getMulticastListenerForView(view, OnItemSelectedListenerMulticast.class)
			.registerWithHighPriority(this);
	}

	@Override
	public Object get() {
		if(getView()==null) return null;
		return getView().getSelectedItem();
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		// Readonly, do nothing
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		this.notifyChanged();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		this.notifyChanged();
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.TwoWay;
	}
}
