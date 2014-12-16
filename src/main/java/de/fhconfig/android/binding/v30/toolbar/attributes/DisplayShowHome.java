package de.fhconfig.android.binding.v30.toolbar.attributes;

import android.support.v7.app.ActionBar;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;

public class DisplayShowHome extends ViewAttribute<BindableToolbar, Boolean> {

	public DisplayShowHome(BindableToolbar view) {
		super(Boolean.class, view, "displayShowHome");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		getHost().getSupportActionBar().setDisplayShowHomeEnabled(newValue.equals(Boolean.TRUE));
	}

	@Override
	public Boolean get() {
		return (getHost().getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_SHOW_HOME)
				== ActionBar.DISPLAY_SHOW_HOME;
	}
}
