package de.fhconfig.android.library.ui.navigationdrawer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import de.fhconfig.android.library.fader.ColorFader;

import static android.graphics.Color.BLACK;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by larsgrefer on 30.01.15.
 */
public class StatusBarColorFadeDrawerListener implements DrawerLayout.DrawerListener {
	private Activity activity;
	private ColorFader colorFader;

	public StatusBarColorFadeDrawerListener(Activity activity, int endColor) {
		this.activity = activity;
		this.colorFader = new ColorFader(getStatusBarColor(), endColor);
	}

	@TargetApi(LOLLIPOP)
	private void setStatusBarColor(int color) {
		if (SDK_INT >= LOLLIPOP) {
			activity.getWindow().setStatusBarColor(color);
		}
	}

	@TargetApi(LOLLIPOP)
	private int getStatusBarColor() {
		if (SDK_INT >= LOLLIPOP) {
			return activity.getWindow().getStatusBarColor();
		} else {
			return BLACK;
		}
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		setStatusBarColor(colorFader.getValue(slideOffset));
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		setStatusBarColor(colorFader.getTo());
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		setStatusBarColor(colorFader.getFrom());
	}

	@Override
	public void onDrawerStateChanged(int newState) {

	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public int getClosedColor() {
		return colorFader.getFrom();
	}

	public void setClosedColor(int closedColor) {
		colorFader.setFrom(closedColor);
	}

	public int getColorOpened() {
		return colorFader.getTo();
	}

	public void setColorOpened(int colorOpened) {
		colorFader.setTo(colorOpened);
	}

	public void setColors(int colorClosed, int colorOpened) {
		colorFader.setBounds(colorClosed, colorOpened);
	}
}
