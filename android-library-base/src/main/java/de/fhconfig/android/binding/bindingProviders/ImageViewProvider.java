package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.ImageView;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.imageView.SourceViewAttribute;


public class ImageViewProvider extends BindingProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof ImageView)) return null;
		if (attributeId.equals("source")) {
			return
					(ViewAttribute<Tv, ?>) new SourceViewAttribute((ImageView) view);
		}
		return null;
	}
}
