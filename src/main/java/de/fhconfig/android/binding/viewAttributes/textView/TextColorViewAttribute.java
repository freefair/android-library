package de.fhconfig.android.binding.viewAttributes.textView;

import android.graphics.Color;
import android.widget.TextView;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;


/**
 * Text Color of Text View
 * Similar to android:textColor
 *
 * @author andy
 * @name textColor
 * @widget TextView
 * @type Integer
 * @accepts Integer
 * @category simple color
 * @related http://developer.android.com/reference/android/widget/TextView.html
 */
public class TextColorViewAttribute extends ViewAttribute<TextView, Integer> {

	public TextColorViewAttribute(TextView view) {
		super(Integer.class, view, "textColor");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			getView().setTextColor(Color.RED);
			return;
		}
		if (newValue instanceof Integer) {
			getView().setTextColor((Integer) newValue);
		}
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}

	@Override
	public Integer get() {
		return null;
	}
}
