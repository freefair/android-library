package de.fhconfig.android.binding.viewAttributes.adapterView.listView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.Collection;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Observer;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.exception.AttributeNotDefinedException;
import de.fhconfig.android.binding.listeners.OnItemClickListenerMulticast;

/**
 * Checked Item Position
 * Note the name is very similar to checkedItemPositions (with s)
 * This one only applies to ListViews with CHOIC_MODE_SINGLE and return the checked item positions
 * You can also change the item position by specifying Integer value to binded observable
 *
 * @author andy
 * @name checkedItemPosition
 * @widget ListView
 * @type Integer
 * @accepts Integer
 * @category list
 * @related
 */
public class CheckedItemPositionViewAttribute extends ViewAttribute<ListView, Integer>
		implements OnItemClickListener, Observer {

	private int mValue;

	public CheckedItemPositionViewAttribute(ListView view) {
		super(Integer.class, view, "checkedItemPosition");
		Binder.getMulticastListenerForView(view, OnItemClickListenerMulticast.class)
				.register(this);
		try {
			Binder.getAttributeForView(view, "itemSource").subscribe(this);
		} catch (AttributeNotDefinedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer get() {
		return mValue;
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (!(newValue instanceof Integer)) return;
		if (getView().getChoiceMode() != ListView.CHOICE_MODE_SINGLE) return;
		getView().setItemChecked((Integer) newValue, true);
		mValue = (Integer) newValue;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (!getView().equals(parent)) return;
		mValue = getView().getCheckedItemPosition();
		this.notifyChanged();
	}

	public void onPropertyChanged(IObservable<?> prop,
	                              Collection<Object> initiators) {
		getView().setItemChecked(mValue, true);
	}
}
