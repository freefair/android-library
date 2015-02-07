package de.fhconfig.android.binding.viewAttributes.textView;

import android.graphics.Typeface;
import android.widget.TextView;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;

/**
 * Typeface of Text View
 *
 * @author andy
 * @name typeface
 * @widget TextView
 * @type Typeface
 * @accepts Typeface
 * @category simple
 * @related http://developer.android.com/reference/android/widget/TextView.html
 * @converter TYPEFACE_FROM_ASSET
 */
public class TypefaceViewAttribute extends ViewAttribute<TextView, Typeface> {

	public TypefaceViewAttribute(TextView view) {
		super(Typeface.class, view, "typeface");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			return;
		}
		if (newValue instanceof Typeface) {
			getView().setTypeface((Typeface) newValue);
		}
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}

	@Override
	public Typeface get() {
		return null;
	}
}
