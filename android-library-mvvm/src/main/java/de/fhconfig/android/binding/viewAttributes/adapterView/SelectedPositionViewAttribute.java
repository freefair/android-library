package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnItemSelectedListenerMulticast;

/**
 * The Selected Position of Adapter View
 * similar to calling AdapterView.setSelection() and changes on AdapterView.OnItemSelectedListener
 *
 * @author andy
 * @name selectedPosition
 * @widget ListView
 * @type Integer
 * @accepts Integer
 * @category list
 * @related
 */
public class SelectedPositionViewAttribute extends ViewAttribute<AdapterView<?>, Integer> implements OnItemSelectedListener {
	private int mValue = 0;

	public SelectedPositionViewAttribute(AdapterView<?> view) {
		super(Integer.class, view, "selectedPosition");
		Binder.getMulticastListenerForView(view, OnItemSelectedListenerMulticast.class).register(this);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (!(newValue instanceof Integer)) return;

		getView().setSelection((Integer) newValue);
		mValue = (Integer) newValue;
	}

	@Override
	public Integer get() {
		return mValue;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
	                           long id) {
		mValue = pos;
		this.notifyChanged();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		mValue = -1;
		this.notifyChanged();
	}
}
