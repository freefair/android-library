package de.fhconfig.android.binding.converters;

import android.graphics.drawable.Drawable;

import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.DynamicObject;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.collections.ArrayListObservable;
import de.fhconfig.android.binding.viewAttributes.tabHost.Tab;

@SuppressWarnings("rawtypes")
public class TAB extends Converter<ArrayListObservable> {

	public TAB(IObservable<?>[] dependents) {
		super(ArrayListObservable.class, dependents);
	}

	@Override
	public ArrayListObservable<Tab> calculateValue(Object... args) throws Exception {
		ArrayListObservable<Tab> tabs = new ArrayListObservable<Tab>(Tab.class);
		for (int i = 0; i < args.length; i++) {
			if (!(args[i] instanceof DynamicObject)) {
				continue;
			}
			DynamicObject t = (DynamicObject) args[i];
			if (!t.observableExists("tag")) continue;
			try {
				Tab tab = new Tab((String) t.getObservableByName("tag").get());
				if (t.observableExists("activity")) {
					tab.Activity.set((String) t.getObservableByName("activity").get());
				} else if (t.observableExists("viewId")) {
					tab.ViewId.set((Integer) t.getObservableByName("viewId").get());
				} else {
					// Invalid
					BindingLog.warning("TAB Converter", "Cannot parse tab");
					continue;
				}
				if (!t.observableExists("label"))
					continue;

				tab.Label.set((String) t.getObservableByName("label").get());
				if (t.observableExists("icon")) {
					Object iconObj = t.getObservableByName("icon").get();
					if (iconObj instanceof Integer) {
						Drawable d = getContext().getResources().getDrawable((Integer) iconObj);
						tab.Icon.set(d);
					} else if (iconObj instanceof Drawable) {
						tab.Icon.set((Drawable) t.getObservableByName("icon").get());
					}
				}
				tabs.add(tab);
			} catch (Exception e) {
				continue;
			}
		}
		return tabs;
	}

}
