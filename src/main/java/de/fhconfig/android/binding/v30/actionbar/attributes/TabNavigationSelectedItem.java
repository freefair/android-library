package de.fhconfig.android.binding.v30.actionbar.attributes;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class TabNavigationSelectedItem extends ViewAttribute<BindableActionBar, Object>{

	public TabNavigationSelectedItem(BindableActionBar view) {
		super(Object.class, view, "tabNavigationSelectedItem");
	}

	private Object mValue;
	
	@Override
	protected void doSetAttributeValue(Object newValue) {
		this.mValue = newValue;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.TwoWay;
	}

	@Override
	public Object get() {
		return mValue;
	}
}
