package de.fhconfig.android.binding.viewAttributes.textView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.fhconfig.android.binding.ViewAttribute;

public class GravityViewAttribute extends ViewAttribute<TextView, Object> {
	public GravityViewAttribute(TextView view) {
		super(Object.class, view, "gravity");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if(newValue instanceof Integer) {
			getView().setGravity((Integer) newValue);
		} else if (newValue instanceof String) {
			String str = (String)newValue;

			String[] split = str.split("\\|");
			int val = 0;
			for(String s : split)
			{
				switch (s.trim().toLowerCase())
				{
					case "center_horizontal":
						val |= Gravity.CENTER_HORIZONTAL;
						break;
					case "center_vertical":
						val |= Gravity.CENTER_VERTICAL;
						break;
					case "center":
						val |= Gravity.CENTER;
						break;
					case "top":
						val |= Gravity.TOP;
						break;
					case "bottom":
						val |= Gravity.BOTTOM;
						break;
					case "left":
						val |= Gravity.LEFT;
						break;
					case "right":
						val |= Gravity.RIGHT;
						break;
				}
			}
			getView().setGravity(val);
		}
	}

	@Override
	public Object get() {
		return null;
	}
}
