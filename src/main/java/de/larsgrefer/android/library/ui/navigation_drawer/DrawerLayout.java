package de.larsgrefer.android.library.ui.navigation_drawer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;

public class DrawerLayout extends android.support.v4.widget.DrawerLayout {
	private ActionBarDrawerToggle mActionBarDrawerToggle;

	public DrawerLayout(Context context) {
		super(context);
		init((Activity) context);
	}

	public DrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init((Activity) context);
	}

	public DrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init((Activity) context);
	}

	private void init(Activity context) {
		mActionBarDrawerToggle = new ActionBarDrawerToggle(context, this, android.R.string.ok, android.R.string.cancel);
		this.setDrawerListener(mActionBarDrawerToggle);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(getViewTreeObserver() != null)
		{
			mActionBarDrawerToggle.syncState();
		}
	}
}
