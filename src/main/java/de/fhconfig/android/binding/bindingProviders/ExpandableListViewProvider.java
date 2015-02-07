package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.ExpandableListView;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.adapterView.ExpandableListView_ItemSourceViewAttribute;
import de.fhconfig.android.binding.viewAttributes.adapterView.listView.expandableListView.AdapterViewAttribute;

public class ExpandableListViewProvider extends BindingProvider {

	@SuppressWarnings({"unchecked"})
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(
			View view, String attributeId) {
		if (!(view instanceof ExpandableListView))
			return null;
		if (attributeId.equals("itemSource"))
			return (ViewAttribute<Tv, ?>)
					new ExpandableListView_ItemSourceViewAttribute((ExpandableListView) view);
		if (attributeId.equals("adapter"))
			return (ViewAttribute<Tv, ?>)
					new AdapterViewAttribute((ExpandableListView) view);
		return null;
	}
}
