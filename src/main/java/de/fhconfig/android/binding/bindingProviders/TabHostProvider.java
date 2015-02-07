package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.TabHost;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.tabHost.TabsViewAttribute;


public class TabHostProvider extends BindingProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof TabHost)) return null;
		if (attributeId.equals("tabs")) {
			return
					(ViewAttribute<Tv, ?>) new TabsViewAttribute((TabHost) view);
		}
		return null;
	}
}