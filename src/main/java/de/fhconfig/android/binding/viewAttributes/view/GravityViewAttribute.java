package de.fhconfig.android.binding.viewAttributes.view;

import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.fhconfig.android.binding.ViewAttribute;

public class GravityViewAttribute extends ViewAttribute<View, Object> {
	public GravityViewAttribute(View view) {
		super(Object.class, view, "gravity");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		try {
			Method setGravity = getView().getClass().getMethod("setGravity");
			if (newValue instanceof Integer) {
				setGravity.invoke(getView(), newValue);
			}
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			Log.e(getClass().getName(), "Error while bind viewparameter gravity.", e);
		}
	}

	@Override
	public Object get() {
		return null;
	}
}
