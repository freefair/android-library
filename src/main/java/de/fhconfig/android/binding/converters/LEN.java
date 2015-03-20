package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

/**
 * Length of the given String, if provided is an object, object.toString().length() will be evaluated
 *
 * @author andy
 * @usage str
 * @arg str String
 * @return integer
 */
public class LEN extends Converter<Integer> {
	public LEN(IObservable<?>[] dependents) {
		super(Integer.class, dependents);
	}

	@Override
	public Integer calculateValue(Object... args) throws Exception {
		return args[0].toString().length();
	}
}
