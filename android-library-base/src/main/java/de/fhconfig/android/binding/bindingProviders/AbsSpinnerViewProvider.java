package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.AbsSpinner;

import de.fhconfig.android.binding.ViewAttribute;


public class AbsSpinnerViewProvider extends BindingProvider {

	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof AbsSpinner)) return null;
		return null;
	}
}
