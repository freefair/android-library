package de.fhconfig.android.binding.viewAttributes.adapterView.listView;

import android.widget.Filter;
import android.widget.ListView;

import de.fhconfig.android.binding.ViewAttribute;

/**
 * Filter for performing filtering on ListViews
 *
 * @author andy
 * @name filter
 * @widget ListView
 * @type Filter
 * @accepts Filter
 * @category list
 * @category filter
 * @related http://developer.android.com/reference/android/widget/Filter.html
 */
public class FilterViewAttribute extends ViewAttribute<ListView, Filter> {
	private Filter mValue;

	public FilterViewAttribute(ListView view) {
		super(Filter.class, view, "filter");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue instanceof Filter) {
			mValue = (Filter) newValue;
		}
	}

	@Override
	public Filter get() {
		return mValue;
	}
}
