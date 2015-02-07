package de.fhconfig.android.binding.observables;

import java.util.Collection;

import de.fhconfig.android.binding.Observable;


public class StringObservable extends Observable<String> {
	public StringObservable() {
		super(String.class);
	}

	public StringObservable(String value) {
		super(String.class, value);
	}

	@Override
	public void _setObject(Object newValue,
	                       Collection<Object> initiators) {
		if (newValue != null)
			this.set(newValue.toString(), initiators);
	}
}
