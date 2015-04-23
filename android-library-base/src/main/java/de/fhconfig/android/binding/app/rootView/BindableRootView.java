package de.fhconfig.android.binding.app.rootView;

import android.content.Context;

import de.fhconfig.android.binding.widgets.BindableFrameLayout;

/**
 * Default root view for the Activity
 *
 * @author andy
 */
public class BindableRootView extends BindableFrameLayout {

	public BindableRootView(Context context) {
		super(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		super.setLayoutParams(params);
	}
}
