package de.fhconfig.android.binding.v30.bindingProviders;

import android.view.View;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.bindingProviders.BindingProvider;
import de.fhconfig.android.binding.v30.viewAttributes.OnAttachViewAttributeV30;
import de.fhconfig.android.binding.v30.viewAttributes.OnDetachViewAttributeV30;

public class ViewProviderV30 extends BindingProvider{
	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(
			View view, String attributeId) {
		if (attributeId.equals("onAttach"))
			return (ViewAttribute<Tv, ?>) new OnAttachViewAttributeV30(view);
		if (attributeId.equals("onDetach"))
			return (ViewAttribute<Tv, ?>) new OnDetachViewAttributeV30(view);				
		return null;
	}
}
