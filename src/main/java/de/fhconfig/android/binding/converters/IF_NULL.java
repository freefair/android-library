package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Undetermined;


/**
 * Do test on condition, if it is null, return arg_if_null, else arg_if_not
 *
 * @usage condition arg_if_null arg_if_not
 * @arg condition boolean
 * @arg arg_if_null Object
 * @arg arg_if_not Object
 * @return Object
 */

public class IF_NULL extends Converter<Object> implements Undetermined {

	public IF_NULL(IObservable<?>[] dependents) {
		super(Object.class, dependents);
	}

	@Override
	public Object calculateValue(Object... args) throws Exception {
		if (args.length == 2) {
			if (args[0] == null) {
				return args[0];
			} else {
				return args[1];
			}
		}
		if (args.length == 3) {
			if (args[0] != null) {
				return args[2];
			} else {
				return args[1];
			}
		}
		return null;
	}
}
