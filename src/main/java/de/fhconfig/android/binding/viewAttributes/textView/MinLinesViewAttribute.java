package de.fhconfig.android.binding.viewAttributes.textView;

import android.widget.TextView;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;

/**
 * Min Lines of Text View
 * Similar to android:minLines
 *
 * @author andy
 * @name minLines
 * @widget TextView
 * @type Integer
 * @accepts Integer
 * @category simple
 * @related http://developer.android.com/reference/android/widget/TextView.html
 */
public class MinLinesViewAttribute extends ViewAttribute<TextView, Integer> {
	public MinLinesViewAttribute(TextView view) {
		super(Integer.class, view, "minLines");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			getView().setMaxLines(1);
			return;
		}
		if (newValue instanceof Integer) {
			getView().setMinLines((Integer) newValue);
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
