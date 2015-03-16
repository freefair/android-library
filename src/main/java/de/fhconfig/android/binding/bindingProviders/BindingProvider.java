package de.fhconfig.android.binding.bindingProviders;

import android.view.View;

import de.fhconfig.android.binding.ViewAttribute;


/**
 * Base class for binding providers. Any special types of views should also inherit this
 * to provide binding syntax parsing and view attributes creation
 *
 * @author andytsui
 */
public abstract class BindingProvider {
	public abstract <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId);

	// A provider is suppose to be stateless, thus, same class name is enough
	@Override
	public boolean equals(Object o) {
		return this.getClass().getName().equals(o.getClass().getName());
	}
}
