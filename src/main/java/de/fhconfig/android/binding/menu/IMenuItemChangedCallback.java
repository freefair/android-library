package de.fhconfig.android.binding.menu;

import de.fhconfig.android.binding.IObservable;

public interface IMenuItemChangedCallback {
	public void onItemChanged(IObservable<?> prop, AbsMenuBridge item);
}
