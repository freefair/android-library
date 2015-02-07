package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.widget.ExpandableListView;

import de.fhconfig.android.binding.ViewAttribute;

/**
 * Item Source for Child items in Expandable List View.
 * Note that it must be in String (quoted) format.
 * For example:
 * ItemVM : RowModel
 * + ArrayList<ChildItem> SubItems
 * <p/>
 * then in XML:
 * binding:childItemSource="'SubItems'"
 *
 * @author andy
 * @name childItemSource
 * @widget ExpandableListView
 * @type String
 * @accepts Object evaluated with toString()
 * @category expandable-list
 * @related
 */
public class ChildItemSourceViewAttribute extends ViewAttribute<ExpandableListView, String> {

	private String mValue;

	public ChildItemSourceViewAttribute(ExpandableListView view) {
		super(String.class, view, "childItemSource");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue != null)
			mValue = newValue.toString();
	}

	@Override
	public String get() {
		return mValue;
	}

}
