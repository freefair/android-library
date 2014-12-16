package de.fhconfig.android.binding.observables;

import de.fhconfig.android.binding.Observable;

public class LongObservable extends Observable<Long> {
	public LongObservable() {
		super(Long.class);
	}
	
	public LongObservable(long value){
		super(Long.class, value);
	}
}
