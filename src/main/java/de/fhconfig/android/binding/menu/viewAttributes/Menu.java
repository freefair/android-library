package de.fhconfig.android.binding.menu.viewAttributes;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.menu.BindableOptionsMenu;

public class Menu extends ViewAttribute<BindableOptionsMenu, Integer> {
	private int mId = -1;

	public Menu(BindableOptionsMenu view) {
		super(Integer.class, view, "Menu");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue instanceof Integer) {
			int newId = (Integer) newValue;
			if (newId != mId) {
				mId = newId;
				getHost().invalidate();
			}
		} else {
			mId = -1;
		}
	}

	@Override
	public Integer get() {
		return mId;
	}
}
