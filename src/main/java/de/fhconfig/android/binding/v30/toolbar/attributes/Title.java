package de.fhconfig.android.binding.v30.toolbar.attributes;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;

public class Title extends ViewAttribute<BindableToolbar, CharSequence> {

	public Title(BindableToolbar view) {
		super(CharSequence.class, view, "title");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue == null) {
			getHost().setTitle("");
			return;
		}
		if (newValue instanceof CharSequence) {
			getHost().setTitle((CharSequence) newValue);
			return;
		}
		getHost().setTitle(newValue.toString());
	}

	@Override
	public CharSequence get() {
		return getHost().getTitle();
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}
}
