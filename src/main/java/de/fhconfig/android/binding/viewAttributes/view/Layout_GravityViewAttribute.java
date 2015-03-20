package de.fhconfig.android.binding.viewAttributes.view;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import de.fhconfig.android.binding.ViewAttribute;

public class Layout_GravityViewAttribute extends ViewAttribute<View, Object> {
	public Layout_GravityViewAttribute(View view) {
		super(Object.class, view, "layout_gravity");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		View view = getView();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
		if (newValue instanceof Integer) {
			layoutParams.gravity = (int) newValue;
		} else if (newValue instanceof String) {
			String str = (String) newValue;
			if (str.equals("right"))
				layoutParams.gravity = Gravity.RIGHT;
			else if (str.equals("left"))
				layoutParams.gravity = Gravity.LEFT;
			else if (str.equals("top"))
				layoutParams.gravity = Gravity.TOP;
			else if (str.equals("bottom"))
				layoutParams.gravity = Gravity.BOTTOM;
		}
		view.setLayoutParams(layoutParams);
	}

	@Override
	public Object get() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getView().getLayoutParams());
		return layoutParams.gravity;
	}
}
