package de.fhconfig.android.binding.observables;

import de.fhconfig.android.binding.Observable;

public class CharSequenceObservable extends Observable<CharSequence> {
	public CharSequenceObservable() {
		super(CharSequence.class);
	}

	public CharSequenceObservable(CharSequence value) {
		super(CharSequence.class, value);
	}
}
