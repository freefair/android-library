package de.fhconfig.android.binding.converters;

import android.text.Html;
import android.text.Spanned;

import de.fhconfig.android.binding.IObservable;

/**
 * Concat, Styles and Convert HTML Charsequences into Styled Spanned.
 *
 * @usage str str ...
 * @arg str String If supplied Object, it will call Object.toString()
 * @return android.text.Spanned formatted with HTML tags in string
 */
public class HTML extends CONCAT {
	public HTML(IObservable<?>[] dependents) {
		super(dependents);
	}

	@Override
	public Spanned calculateValue(Object... args) throws Exception {
		return Html.fromHtml(super.calculateValue(args).toString());
	}
}
