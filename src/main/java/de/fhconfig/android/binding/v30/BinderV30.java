package de.fhconfig.android.binding.v30;

import android.app.Application;
import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingMap;

/**
 * @author andy
 * @deprecated Use Binder instead
 */
public class BinderV30 extends Binder {
	/**
	 * @param application
	 * @deprecated Use Binder.init(Application, IKernel) instead
	 */
	@Deprecated
	public static void init(Application application) {
		Binder.init(application, new DefaultKernelV30());
	}

	/**
	 * Merge the BindingMap in the view with the supplied new map
	 * If both keys are existed, it will be replaced by the new map
	 *
	 * @param view
	 * @param map
	 */
	@Deprecated
	public static void mergeBindingMap(View view, BindingMap map) {
		BindingMap original = getBindingMapForView(view);
		if (original == null) {
			putBindingMapToView(view, map);
			return;
		}

		for (String key : map.getAllKeys()) {
			original.put(key, map.get(key));
		}
		putBindingMapToView(view, original);
	}
}
