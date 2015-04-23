package de.fhconfig.android.binding.viewAttributes.templates;

import de.fhconfig.android.binding.Observable;

public class LayoutObservable extends Observable<Layout> {

	public LayoutObservable(Layout initValue) {
		super(Layout.class, initValue);
	}

	public LayoutObservable() {
		super(Layout.class);
	}
}
