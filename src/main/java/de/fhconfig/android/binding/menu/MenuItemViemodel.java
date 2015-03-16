package de.fhconfig.android.binding.menu;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.IObservableCollection;

public class MenuItemViemodel {
	public Command onClick;
	public IObservable<?> title;
	public IObservable<?> visible;
	public IObservable<?> enabled;
	public IObservable<?> checked;
	public IObservable<?> icon;
	public IObservableCollection<MenuItemViemodel> group;
}
