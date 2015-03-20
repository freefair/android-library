package de.fhconfig.android.binding.v30.bindingProviders;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.bindingProviders.BindingProvider;
import de.fhconfig.android.binding.v30.viewAttributes.absListView.ModalCheckedItemPositions;
import de.fhconfig.android.binding.v30.viewAttributes.absListView.MultiChoiceMode;

public class ListViewProviderV30 extends BindingProvider {
	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(
			View view, String attributeId) {
		if (!(view instanceof ListView)) return null;
		if (attributeId.equals("multiChoiceMode"))
			return (ViewAttribute<Tv, ?>) new MultiChoiceMode((AbsListView) view);
		if (attributeId.equals("modalCheckedItemPositions"))
			return (ViewAttribute<Tv, ?>) new ModalCheckedItemPositions((ListView) view);
		return null;
	}
}
