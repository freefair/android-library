package de.fhconfig.android.binding.observables;

import de.fhconfig.android.binding.Observable;

public class FloatObservable extends Observable<Float> {

	public FloatObservable() {
		super(Float.class);
	}

	public FloatObservable(Float initValue) {
		super(Float.class, initValue);
	}
}
