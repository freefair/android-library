package de.fhconfig.android.binding.v30.actionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Locale;

import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.ViewAttribute;

@SuppressLint("ViewConstructor")
public class BindableActionBar extends View implements IBindableView<BindableActionBar> {

	WeakReference<ActionBarActivity> mActivityRef;

	public BindableActionBar(ActionBarActivity context) {
		super(context);
		mActivityRef = new WeakReference<>(context);
	}

	public ActionBar getSupportActionBar() {
		if (mActivityRef == null || mActivityRef.get() == null)
			return null;
		return mActivityRef.get().getSupportActionBar();
	}

	public Activity getActivity() {
		if (mActivityRef == null || mActivityRef.get() == null)
			return null;
		return mActivityRef.get();
	}

	public ViewAttribute<? extends View, ?> createViewAttribute(
			String attributeId) {
		try {
			String capId = attributeId.substring(0, 1).toUpperCase(Locale.US) + attributeId.substring(1);
			String className = "de.fhconfig.android.binding.v30.actionbar.attributes." + capId;
			return (ViewAttribute<?, ?>) Class.forName(className)
					.getConstructor(BindableActionBar.class)
					.newInstance((BindableActionBar) this);
		} catch (Exception e) {
			BindingLog.warning("ActionBarAttributeBinder", "Attribute not found");
			return null;
		}
	}
}