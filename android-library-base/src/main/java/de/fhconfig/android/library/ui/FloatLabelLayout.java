package de.fhconfig.android.library.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @deprecated Use {@link android.support.design.widget.TextInputLayout} instead
 *
 */
@Deprecated
public class FloatLabelLayout extends android.support.design.widget.TextInputLayout {
	public FloatLabelLayout(Context context) {
		super(context);
	}

	public FloatLabelLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FloatLabelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
}