package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.ProgressBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.progressBar.ProgressViewAttribute;
import de.fhconfig.android.binding.viewAttributes.progressBar.SecondaryProgressViewAttribute;


public class ProgressBarProvider extends BindingProvider {
	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof ProgressBar)) return null;
		if (attributeId.equals("progress")) {
			return (ViewAttribute<Tv, ?>)
					new ProgressViewAttribute((ProgressBar) view);
		}
		if (attributeId.equals("secondaryProgress")) {
			return (ViewAttribute<Tv, ?>)
					new SecondaryProgressViewAttribute((ProgressBar) view);
		}
		return null;
	}
}
