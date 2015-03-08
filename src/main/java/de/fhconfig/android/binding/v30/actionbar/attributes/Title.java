package de.fhconfig.android.binding.v30.actionbar.attributes;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class Title extends ViewAttribute<BindableActionBar, CharSequence> {

	public Title(BindableActionBar view) {
		super(CharSequence.class, view, "title");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue == null) {
			getHost().getSupportActionBar().setTitle("");
			return;
		}
		if (newValue instanceof CharSequence) {
			getHost().getSupportActionBar().setTitle((CharSequence) newValue);
			return;
		}
		getHost().getSupportActionBar().setTitle(newValue.toString());
	}

	@Override
	public CharSequence get() {
		return getHost().getSupportActionBar().getTitle();
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}
}
