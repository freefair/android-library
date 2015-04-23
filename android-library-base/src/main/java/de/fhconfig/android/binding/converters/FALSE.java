package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

/**
 * Constant that always return false. Takes no argument
 *
 * @usage
 * @return boolean
 */
public class FALSE extends Converter<Boolean> {

	public FALSE(IObservable<?>[] dependents) {
		super(Boolean.class, dependents);
	}

	@Override
	public Boolean calculateValue(Object... args) throws Exception {
		return false;
	}

}
