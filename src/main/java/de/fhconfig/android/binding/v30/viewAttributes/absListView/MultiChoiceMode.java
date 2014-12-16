package de.fhconfig.android.binding.v30.viewAttributes.absListView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.DynamicObject;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Observer;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.exception.AttributeNotDefinedException;
import de.fhconfig.android.binding.v30.listeners.MultiChoiceModeListenerMulticast;
import de.fhconfig.android.binding.v30.widget.ActionModeBinder;

import java.util.Collection;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;

public class MultiChoiceMode extends ViewAttribute<AbsListView, DynamicObject> 
	implements AbsListView.MultiChoiceModeListener{

	private int mMenuId;
	private Object mModel;
	private IObservable<?> mTitle;
	ListViewMultiChoiceActionModeBinder binder;
	
	public MultiChoiceMode(AbsListView view) {
		super(DynamicObject.class, view, "multiChoiceMode");
		Binder.getMulticastListenerForView(view, MultiChoiceModeListenerMulticast.class)
			.register(this);
		try {
			Binder.getAttributeForView(view, "modalCheckedItemPositions")
				.subscribe(checkedItemPositionsWatcher);
		} catch (AttributeNotDefinedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {		
		try {
			DynamicObject obj = (DynamicObject)newValue;
			mMenuId = (Integer)obj.getObservableByName("menu").get();
			mModel = obj.getObservableByName("model").get();
			mTitle = obj.getObservableByName("title");
		} catch (Exception e) {
			BindingLog.warning("MultiChoiceMode", "Dynamic Object format error; it should be {menu, model}");
		}
	}

	@Override
	public DynamicObject get() {
		return null;
	}

	private Observer checkedItemPositionsWatcher = new Observer(){
		public void onPropertyChanged(IObservable<?> prop,
				Collection<Object> initiators) {
			if (getView().getCheckedItemCount() >0 ) return;
			
			if (binder!=null && binder.getActionMode()!=null){
				binder.getActionMode().finish();
			}
		}
	};
	
	private class ListViewMultiChoiceActionModeBinder extends ActionModeBinder
		implements AbsListView.MultiChoiceModeListener{
		protected ListViewMultiChoiceActionModeBinder(Context context,
				int menuResId, Object model, IObservable<?> title) {
			super(context, menuResId, model, title);
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
		}
	}

	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		if (binder!=null)
			return binder.onActionItemClicked(mode, item);
		return false;
	}

	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		binder = new ListViewMultiChoiceActionModeBinder(getView().getContext(), mMenuId, mModel, mTitle);
		if (binder!=null)
			return binder.onCreateActionMode(mode, menu);
		return false;
	}

	public void onDestroyActionMode(ActionMode mode) {
		if (binder!=null)
			binder.onDestroyActionMode(mode);
	}

	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		if (binder!=null)
			return binder.onPrepareActionMode(mode, menu);
		return false;
	}

	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		if (binder!=null)
			binder.onItemCheckedStateChanged(mode, position, id, checked);
	}
}
