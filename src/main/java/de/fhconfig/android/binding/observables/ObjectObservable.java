package de.fhconfig.android.binding.observables;

import de.fhconfig.android.binding.Observable;

public class ObjectObservable extends Observable<Object> {
	public ObjectObservable() {
		super(Object.class);
	}

	public ObjectObservable(Object value) {
		super(Object.class, value);
	}
}
