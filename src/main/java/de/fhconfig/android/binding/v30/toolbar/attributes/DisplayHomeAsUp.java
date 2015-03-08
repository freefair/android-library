package de.fhconfig.android.binding.v30.toolbar.attributes;

import android.support.v7.app.ActionBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;

public class DisplayHomeAsUp extends ViewAttribute<BindableToolbar, Boolean> {

	public DisplayHomeAsUp(BindableToolbar view) {
		super(Boolean.class, view, "displayHomeAsUp");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		getHost().getSupportActionBar().setDisplayHomeAsUpEnabled(newValue.equals(Boolean.TRUE));
	}

	@Override
	public Boolean get() {
		return (getHost().getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP)
				== ActionBar.DISPLAY_HOME_AS_UP;
	}
}
