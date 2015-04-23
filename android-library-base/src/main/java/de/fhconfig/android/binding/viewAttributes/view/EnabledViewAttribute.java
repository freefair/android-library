package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.ViewAttribute;

/**
 * Enabled State of View
 * similar to android:enabled
 *
 * @author andy
 * @name enabled
 * @widget View
 * @type Boolean
 * @accepts Boolean
 * @category simple
 * @related http://developer.android.com/reference/android/widget/View.html
 */
public class EnabledViewAttribute extends ViewAttribute<View, Boolean> {

	public EnabledViewAttribute(View view, String attributeName) {
		super(Boolean.class, view, attributeName);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			getView().setEnabled(false);
			return;
		}
		if (newValue instanceof Boolean) {
			getView().setEnabled((Boolean) newValue);
		}
	}

	@Override
	public Boolean get() {
		if (getView() == null) return null;
		return getView().isEnabled();
	}
}
