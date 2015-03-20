package de.fhconfig.android.binding.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import de.fhconfig.android.binding.IObservable;

public class MenuGroupBridge extends AbsMenuBridge {

	private IObservable<Boolean> mVisible;

	public MenuGroupBridge(int id, AttributeSet attributes,
	                       Context context, Object model) {
		this(id, attributes, context, model, null);
	}

	@SuppressWarnings("unchecked")
	public MenuGroupBridge(int id, AttributeSet attributes,
	                       Context context, Object model, IMenuItemChangedCallback callback) {
		super(id);

		// Assume id is created
		IObservable<?> temp = getObservableFromStatement(context, attributes, "visible", model, callback);
		if ((temp != null) && (Boolean.class.isAssignableFrom(temp.getType()))) {
			mVisible = (IObservable<Boolean>) temp;
		}
	}

	@SuppressWarnings("unchecked")
	public MenuGroupBridge(int id, MenuItemViemodel model) {
		super(id);
		if (model == null)
			return;

		// Assume id is created
		IObservable<?> temp = model.visible;
		if ((temp != null) && (Boolean.class.isAssignableFrom(temp.getType()))) {
			mVisible = (IObservable<Boolean>) temp;
		}
	}

	@Override
	public void onCreateOptionItem(Menu menu) {
	}

	@Override
	public void onPrepareOptionItem(Menu menu) {
		if (mVisible != null)
			menu.setGroupVisible(mId, mVisible.get());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}
}
