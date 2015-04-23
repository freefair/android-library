package de.fhconfig.android.binding.viewAttributes.autoCompleteTextView;

import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.Tokenizer;

import de.fhconfig.android.binding.ViewAttribute;

/**
 * Tokenizer for MultiAutoCompleteTextView
 *
 * @author andy
 * @name tokenizer
 * @widget MultiAutoCompleteTextView
 * @type Tokenizer
 * @accepts Tokenizer
 * @category list simple
 * @related
 */
public class TokenizerViewAttribute extends ViewAttribute<MultiAutoCompleteTextView, MultiAutoCompleteTextView.Tokenizer> {
	public TokenizerViewAttribute(MultiAutoCompleteTextView view) {
		super(MultiAutoCompleteTextView.Tokenizer.class, view, "tokenizer");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue instanceof MultiAutoCompleteTextView.Tokenizer) {
			getView().setTokenizer((MultiAutoCompleteTextView.Tokenizer) newValue);
		}
	}

	@Override
	public Tokenizer get() {
		return null;
	}
}