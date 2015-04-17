package de.fhconfig.android.binding.bindingProviders;

import android.view.View;

import java.lang.reflect.Method;
import java.util.Locale;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.GenericViewAttribute;


public class GenericViewAttributeProvider extends BindingProvider {

	@Override
	public ViewAttribute<View, ?> createAttributeForView(View view, String attributeId) {
		return tryCreateGenericViewAttribute(view, attributeId);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private ViewAttribute<View, ?> tryCreateGenericViewAttribute(View view,
	                                                             String attributeId) {
		try {
			String capAttrib = attributeId.substring(0, 1).toUpperCase(Locale.US) + attributeId.substring(1);

			Method getter = view.getClass().getMethod("get" + capAttrib);
			Method setter = null;
			try {
				setter = view.getClass().getMethod("set" + capAttrib, getter.getReturnType());
			} catch (Exception e) {
			}

			return new GenericViewAttribute(getter.getReturnType(), view, attributeId, getter, setter);
		} catch (Exception e) {
			return null;
		}
	}
}
