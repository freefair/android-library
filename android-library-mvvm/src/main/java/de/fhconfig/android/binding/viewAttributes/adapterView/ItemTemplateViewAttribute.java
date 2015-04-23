package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.Utility;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;
import de.fhconfig.android.binding.viewAttributes.templates.SingleTemplateLayout;

/**
 * Item Template of Adapter View
 * Template should be specified as Layout, you can bind to Integer value or
 * {@literal @}layout/main_layout
 * <p/>
 * Due to the complexity of this API, we recommend to switch to binding:adapter instead of this
 * e.g.  binding:adapter="ADAPTER({source=..., template=...})
 * which will give more fine-grain control on adapter generated
 *
 * @author andy
 * @name itemSource
 * @widget AdapterView
 * @type de.fhconfig.android.binding.viewAttributes.templates.Layout
 * @accepts de.fhconfig.android.binding.viewAttributes.templates.Layout
 * @accepts Integer
 * @accepts CharSequence
 * @category list
 * @related
 */
public class ItemTemplateViewAttribute extends ViewAttribute<View, Layout> {

	private Layout template;

	public ItemTemplateViewAttribute(View view,
	                                 String attributeName) {
		super(Layout.class, view, attributeName);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue instanceof Layout) {
			template = (Layout) newValue;
		} else if (newValue instanceof Integer) {
			if ((Integer) newValue > 0)
				template = new SingleTemplateLayout((Integer) newValue);
		} else if (newValue instanceof CharSequence) {
			int value = Utility.resolveLayoutResource(newValue.toString(), getView().getContext());
			if (value > 0) {
				template = new SingleTemplateLayout((Integer) newValue);
			}
		}
	}

	@Override
	public Layout get() {
		return template;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		if (Integer.class.isAssignableFrom(type) ||
				CharSequence.class.isAssignableFrom(type))
			return BindingType.OneWay;
		if (Layout.class.isAssignableFrom(type)) return BindingType.TwoWay;
		return BindingType.NoBinding;
	}
}
