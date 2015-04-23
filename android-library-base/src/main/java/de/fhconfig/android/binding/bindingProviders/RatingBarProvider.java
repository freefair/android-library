package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.RatingBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.ratingBar.OnRatingChangedViewEvent;
import de.fhconfig.android.binding.viewAttributes.ratingBar.RatingViewAttribute;


public class RatingBarProvider extends BindingProvider {
	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof RatingBar)) return null;
		if (attributeId.equals("rating")) {
			RatingViewAttribute attr = new RatingViewAttribute((RatingBar) view);
			return (ViewAttribute<Tv, ?>) attr;
		}
		if (attributeId.equals("onRatingChanged")) {
			return (ViewAttribute<Tv, ?>) new OnRatingChangedViewEvent((RatingBar) view);
		}
		return null;
	}
}
