package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.viewAttributes.templates.LayoutRowChild;
import de.fhconfig.android.binding.viewAttributes.templates.SingleTemplateLayout;

/**
 * ROW_CHILD this adapter is used for bindable table layouts
 */
public class ROW_CHILD extends Converter<LayoutRowChild> {

	public ROW_CHILD(IObservable<?>[] dependents) {
		super(LayoutRowChild.class, dependents);
	}

	@Override
	public LayoutRowChild calculateValue(Object... args) throws Exception {
		if (args.length < 2) return null;
		if (args[0] == null) return null;

		LayoutRowChild rowChild = new LayoutRowChild(args[0].toString());

		if (args[1] instanceof SingleTemplateLayout) {
			rowChild.setLayoutId(((SingleTemplateLayout) args[1]).getDefaultLayoutId());
		} else if (args[1] != null) {
			rowChild.setLayoutName(args[1].toString());
		}

		// optional colspan
		if (args.length > 2) {
			rowChild.setColspanName(args[2].toString());
		}

		return rowChild;
	}

}
