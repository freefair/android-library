package de.fhconfig.android.binding.v30.actionbar.attributes;

import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class TabNavigationOnItemSelected
		extends ViewEventAttribute<BindableActionBar> {

	public TabNavigationOnItemSelected(BindableActionBar view) {
		super(view, "TabNavigationOnItemSelected");
	}

	@Override
	protected void registerToListener(BindableActionBar view) {
		// nothing, only register in list navigation adapter
	}
}
