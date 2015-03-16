package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

/**
 * Constant converter that always returns null
 *
 * @author andy
 * @usage
 * @return null
 */
public class NULL extends Converter<Object> {

	public NULL(IObservable<?>[] dependents) {
		super(Object.class, dependents);
	}

	@Override
	public Object calculateValue(Object... args) throws Exception {
		return null;
	}

}
