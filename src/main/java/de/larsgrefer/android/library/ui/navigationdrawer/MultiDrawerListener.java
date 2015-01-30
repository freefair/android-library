package de.larsgrefer.android.library.ui.navigationdrawer;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by larsgrefer on 30.01.15.
 */
public class MultiDrawerListener implements DrawerLayout.DrawerListener {
	private List<DrawerLayout.DrawerListener> drawerListeners = new LinkedList<>();

	public void addDrawerListener(DrawerLayout.DrawerListener drawerListener) {
		drawerListeners.add(drawerListener);
	}

	public boolean removeDrawerListener(DrawerLayout.DrawerListener drawerListener) {
		return drawerListeners.remove(drawerListener);
	}

	protected List<DrawerLayout.DrawerListener> getDrawerListeners(){
		return drawerListeners;
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		for(DrawerLayout.DrawerListener drawerListener : drawerListeners){
			drawerListener.onDrawerSlide(drawerView, slideOffset);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		for(DrawerLayout.DrawerListener drawerListener : drawerListeners){
			drawerListener.onDrawerOpened(drawerView);
		}
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		for(DrawerLayout.DrawerListener drawerListener : drawerListeners){
			drawerListener.onDrawerClosed(drawerView);
		}
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		for(DrawerLayout.DrawerListener drawerListener : drawerListeners){
			drawerListener.onDrawerStateChanged(newState);
		}
	}
}
