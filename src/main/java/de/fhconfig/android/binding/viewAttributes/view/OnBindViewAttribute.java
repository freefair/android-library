package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.ViewAttribute;

public class OnBindViewAttribute extends ViewAttribute<View, Object> {

	public OnBindViewAttribute(View view) {
		super(Object.class, view, "onBind");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null)
			return;

		if (newValue instanceof Command) {
			Command cmd = (Command) newValue;
			cmd.Invoke(getView());
		}
	}

	@Override
	public Object get() {
		return null;
	}

}
