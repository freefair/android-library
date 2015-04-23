package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

import de.fhconfig.android.binding.DynamicObject;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Observer;
import de.fhconfig.android.binding.ViewAttribute;

/**
 * Assign a value back to View Model
 * In MVVM, ideally we don't want to let View Model to know much about display logic,
 * but the layout XML is supposed to aware of different device configuration
 * <p/>
 * for example, we can write
 * binding:assign="{ displayInNewActivity=TRUE() }"
 * <p/>
 * so, in the view model, the displayInActivity will set to true.
 * <p/>
 * In this way, we can free the view model from trying to detect what display configuration it is in,
 * because XML layout/resources can be prepared with different configurations (e.g. -land, -port, -v14 etc)
 * <p/>
 * You can assign to multiple properties, since the accepted parameter is dynamic object:
 * <p/>
 * e.g. binding:assign="{ propA = 'AValue', propB = 'BValue', propC = 'CValue' }"
 * <p/>
 * Note: This is currently only attribute that is not related to any view
 *
 * @author andy
 * @name assign
 * @widget View
 * @type de.fhconfig.android.binding.DynamicObject
 * @accepts de.fhconfig.android.binding.DynamicObject
 * @category simple special
 */
public class AssignViewAttribute extends ViewAttribute<View, DynamicObject> {

	IObservable<?> prop, val;

	public AssignViewAttribute(View view) {
		super(DynamicObject.class, view, "assign");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null || !(newValue instanceof DynamicObject)) {
			return;
		}
		DynamicObject value = (DynamicObject) newValue;
		try {
			prop = value.getObservableByName("prop");
			val = value.getObservableByName("value");
			val.subscribe(new Observer() {
				@Override
				public void onPropertyChanged(IObservable<?> prop,
				                              Collection<Object> initiators) {
					prop._setObject(val.get(), initiators);
				}
			});
			ArrayList<Object> initiators = new ArrayList<>();
			initiators.add(this);
			initiators.add(value);
			prop._setObject(val.get(), initiators);
		} catch (Exception e) {
		}
	}

	@Override
	public DynamicObject get() {
		return null;
	}
}
