package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Undetermined;

/**
 * Dynamically evaluate the statement for the with the view_model as Object Context
 *
 * @usage view_model statement
 * @arg view_model Object the Context to evaluate the statement
 * @arg statement String
 * @return Object evaluated result of the statement
 */
public class PROP extends Converter<Object> implements Undetermined {

	public PROP(IObservable<?>[] dependents) {
		super(Object.class, dependents);
	}

	@Override
	public Object calculateValue(Object... args) throws Exception {
		if (args.length < 2) return null;
		if (args[0] == null) return null;
		IObservable<?> childObs =
				Binder.getSyntaxResolver()
						.constructObservableFromStatement(this.getContext(), args[1].toString(), args[0]);
		if (childObs != null)
			return childObs.get();

		return null;
	}
}
