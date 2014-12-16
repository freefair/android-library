package de.fhconfig.android.binding.observables;

import de.fhconfig.android.binding.Observable;

public class IntegerObservable extends Observable<Integer> {
	public IntegerObservable() {
		super(Integer.class);
	}
	
	public IntegerObservable(int value){
		super(Integer.class, value);
	}
}
