package gueei.binding.v30.actionbar.attributes;

import android.support.v7.app.ActionBar;
import gueei.binding.ViewAttribute;
import gueei.binding.v30.actionbar.BindableActionBar;

public class NavigationMode extends ViewAttribute<BindableActionBar, Integer> {

	public NavigationMode(BindableActionBar view) {
		super(Integer.class, view, "displayHomeAsUp");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue instanceof Integer)
			getHost().getSupportActionBar().setNavigationMode((Integer)newValue);
		else
			getHost().getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	@Override
	public Integer get() {
		return getHost().getSupportActionBar().getNavigationMode();
	}
}
