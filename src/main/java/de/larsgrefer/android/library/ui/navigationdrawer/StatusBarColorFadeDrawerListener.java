package de.larsgrefer.android.library.ui.navigationdrawer;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import de.larsgrefer.android.library.fader.ColorFader;

/**
 * Created by larsgrefer on 30.01.15.
 */
public class StatusBarColorFadeDrawerListener implements DrawerLayout.DrawerListener {
	private Activity activity;
	private ColorFader colorFader;

	public StatusBarColorFadeDrawerListener(Activity activity, int endColor){
		this.activity = activity;
		if(Build.VERSION.SDK_INT >= 21){
			this.colorFader = new ColorFader(activity.getWindow().getStatusBarColor(), endColor);
		}
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
			if (Build.VERSION.SDK_INT >= 21) {
				activity.getWindow().setStatusBarColor(colorFader.getValue(slideOffset));
			}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		if (Build.VERSION.SDK_INT >= 21) {
			activity.getWindow().setStatusBarColor(colorFader.getTo());
		}
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		if (Build.VERSION.SDK_INT >= 21) {
			activity.getWindow().setStatusBarColor(colorFader.getFrom());
		}
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

	public void setColors(int colorClosed, int colorOpened){
		colorFader.setBounds(colorClosed, colorOpened);
	}
}
