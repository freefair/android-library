package de.fhconfig.android.binding.v30.toolbar;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.ViewAttribute;

public class BindableToolbar extends Toolbar implements IBindableView<BindableToolbar> {
	private WeakReference<ActionBarActivity> mActivityRef;

	public BindableToolbar(Context context) {
		super(context);
		init((ActionBarActivity) context);
	}

	public BindableToolbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init((ActionBarActivity) context);
	}

	public BindableToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init((ActionBarActivity) context);
	}

	private void init(ActionBarActivity activity) {
		mActivityRef = new WeakReference<>(activity);
		activity.setSupportActionBar(this);
	}

	public ActionBar getSupportActionBar() {
		if (mActivityRef.get() == null)
			return null;
		return mActivityRef.get().getSupportActionBar();
	}

	@Override
	public ViewAttribute<? extends View, ?> createViewAttribute(String attributeId) {
		try {
			String capId = attributeId.substring(0, 1).toUpperCase() + attributeId.substring(1);
			String className = "de.fhconfig.android.binding.v30.toolbar.attributes." + capId;
			return (ViewAttribute<?, ?>) Class.forName(className)
					.getConstructor(BindableToolbar.class)
					.newInstance((BindableToolbar) this);
		} catch (Exception e) {
			BindingLog.warning("ActionBarAttributeBinder", "Attribute not found");
			return null;
		}
	}

	public Context getActivity() {
		return mActivityRef.get();
	}
}
