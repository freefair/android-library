package de.fhconfig.android.binding.v30.toolbar.attributes;

import android.support.v7.app.ActionBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;

public class DisplayShowTitle extends ViewAttribute<BindableToolbar, Boolean> {

	public DisplayShowTitle(BindableToolbar view) {
		super(Boolean.class, view, "displayShowTitle");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		getHost().getSupportActionBar().setDisplayShowTitleEnabled(newValue.equals(Boolean.TRUE));
	}

	@Override
	public Boolean get() {
		return (getHost().getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_SHOW_TITLE)
				== ActionBar.DISPLAY_SHOW_TITLE;
	}
}
