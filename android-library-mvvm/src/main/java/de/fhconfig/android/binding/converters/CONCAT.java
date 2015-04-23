package de.fhconfig.android.binding.converters;

import android.text.SpannableStringBuilder;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

/**
 * CONCAT the given strings to one string
 *
 * @author andy
 * @usage str str ...
 * @arg str String
 * @return String
 */
public class CONCAT extends Converter<CharSequence> {

	public CONCAT(IObservable<?>[] dependents) {
		super(CharSequence.class, dependents);
	}

	@Override
	public CharSequence calculateValue(Object... args) throws Exception {
		int len = args.length;
		SpannableStringBuilder result = new SpannableStringBuilder("");
		for (int i = 0; i < len; i++) {
			if (args[i] == null) continue;
			if (args[i] instanceof CharSequence)
				result.append((CharSequence) args[i]);
			else
				result.append(args[i].toString());
		}
		return result.toString();
	}
}
