package de.fhconfig.android.binding.viewAttributes.textView;

import android.widget.TextView;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;


public class TextLinesViewAttribute extends ViewAttribute<TextView, Integer> {
	private Mode mode = Mode.MinLines;

	public TextLinesViewAttribute(TextView view, Mode mode) {
		super(Integer.class, view, getAttributeName(mode));
		this.mode = mode;
	}

	private static String getAttributeName(Mode mode) {
		if (mode == Mode.MinLines)
			return "minLines";
		else
			return "maxLines";
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			getView().setMaxLines(1);
			return;
		}
		if (newValue instanceof Integer) {
			if (mode == Mode.MinLines)
				getView().setMinLines((Integer) newValue);
			else
				getView().setMaxLines((Integer) newValue);
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

	public static enum Mode {
		MinLines, MaxLines
	}
}
