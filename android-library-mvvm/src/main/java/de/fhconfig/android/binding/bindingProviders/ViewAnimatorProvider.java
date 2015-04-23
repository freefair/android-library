package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.ViewAnimator;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.viewAnimator.ChildViewsViewAttribute;
import de.fhconfig.android.binding.viewAttributes.viewAnimator.DisplayedChildViewAttribute;


public class ViewAnimatorProvider extends BindingProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof ViewAnimator)) return null;
		if (attributeId.equals("displayedChild")) {
			DisplayedChildViewAttribute attr = new
					DisplayedChildViewAttribute((ViewAnimator) view);
			return (ViewAttribute<Tv, ?>) attr;
		}
		if (attributeId.equals("childViews")) {
			ChildViewsViewAttribute attr = new
					ChildViewsViewAttribute((ViewAnimator) view);
			return (ViewAttribute<Tv, ?>) attr;
		}
		return null;
	}
}
