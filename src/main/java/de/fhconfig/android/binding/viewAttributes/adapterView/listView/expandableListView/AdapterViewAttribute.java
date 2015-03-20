package de.fhconfig.android.binding.viewAttributes.adapterView.listView.expandableListView;

import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.collections.LazyLoadAdapter;

/**
 * To Provide adapter to ExpandableListView
 *
 * @param <T>
 * @author andy
 * @name adapter
 * @widget ExpandableListView
 * @type ExpandableListAdapter
 * @accepts ExpandableListAdapter
 * @category expandable-list
 * @related
 */
public class AdapterViewAttribute extends ViewAttribute<ExpandableListView, ExpandableListAdapter> {
	public AdapterViewAttribute(ExpandableListView view) {
		super(ExpandableListAdapter.class, view, "adapter");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue instanceof ExpandableListAdapter) {
			getView().setAdapter((ExpandableListAdapter) newValue);
			if (newValue instanceof LazyLoadAdapter) {
				if (getView() instanceof AbsListView)
					((LazyLoadAdapter) newValue).setRoot((AbsListView) getView());
			}
		}
	}

	@Override
	public ExpandableListAdapter get() {
		if (getView() == null) return null;
		return (ExpandableListAdapter) getView().getAdapter();
	}
}