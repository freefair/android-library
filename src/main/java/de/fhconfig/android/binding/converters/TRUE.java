package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

/**
 * Constant that always return true. Takes no argument
 *
 * @usage
 * @return boolean
 */
public class TRUE extends Converter<Boolean> {

	public TRUE(IObservable<?>[] dependents) {
		super(Boolean.class, dependents);
	}

	@Override
	public Boolean calculateValue(Object... args) throws Exception {
		return true;
	}

}
