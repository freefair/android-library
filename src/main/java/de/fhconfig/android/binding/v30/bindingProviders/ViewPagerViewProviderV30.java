package de.fhconfig.android.binding.v30.bindingProviders;

import android.support.v4.view.ViewPager;
import android.view.View;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.bindingProviders.BindingProvider;
import de.fhconfig.android.binding.v30.viewAttributes.adapterView.viewPager.AdapterViewAttribute;

public class ViewPagerViewProviderV30 extends BindingProvider {
	@SuppressWarnings({"unchecked"})
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(
			View view, String attributeId) {
		if (!(view instanceof ViewPager))
			return null;
		try {
			if (attributeId.equals("adapter")) {
				return (ViewAttribute<Tv, ?>) new AdapterViewAttribute((ViewPager) view);
			}
		} catch (Exception e) {
			// Actually it should never reach this statement
		}
		return null;
	}
}
