package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.lang.ref.WeakReference;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnItemSelectedListenerMulticast;

public class SelectedObjectViewAttribute extends ViewAttribute<AdapterView<?>, Object> implements OnItemSelectedListener {
	WeakReference<Object> selectedItem;
	int mPosition = -1;

	public SelectedObjectViewAttribute(AdapterView<?> view) {
		super(Object.class, view, "selectedObject");
		Binder.getMulticastListenerForView(view, OnItemSelectedListenerMulticast.class).register(this);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		Object selected = getView().getSelectedItem();
		Object o = null;

		int pos = -1;

		if (newValue != null) {
			int c = getView().getAdapter().getCount();

			for (int i = 0; i < c; i++) {
				o = getView().getAdapter().getItem(i);
				if (o == null)
					continue;
				if (o.equals(newValue)) {
					pos = i;
					break;
				}
			}
		}

		if (selected != null && o != null)
			if (selected.equals(o))
				return;

		selectedItem = new WeakReference<>(newValue);
		getView().setSelection(pos);
	}

	@Override
	public Object get() {
		if (selectedItem == null || selectedItem.get() == null)
			return null;

		return selectedItem.get();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
	                           long id) {

		if (pos < 0 || pos > getView().getAdapter().getCount())
			return;

		selectedItem = new WeakReference<>(getView().getAdapter().getItem(pos));
		this.notifyChanged();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		selectedItem = null;
		this.notifyChanged();
	}
}
