package de.fhconfig.android.binding.menu;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.ConstantObservable;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.ISyntaxResolver.SyntaxResolveException;
import de.fhconfig.android.binding.Observer;

import java.util.Collection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

/**
 * An instance of AbsMenuBridge maps to one xml
 * @author andy
 *
 */
public abstract class AbsMenuBridge {
	
	// Factory method
	public static AbsMenuBridge create
		(String nodeName, int id, AttributeSet attrs, Context context, 
				Object model, IMenuItemChangedCallback callback){
		AbsMenuBridge item;
		
		if ("item".equals(nodeName)){
			item = new MenuItemBridge(id, attrs, context, model, callback);
		}else if ("group".equals(nodeName)){
			item = new MenuGroupBridge(id, attrs, context, model);
		}else{
			item = null;
		}
		
		return item;
	}
	
	protected final int mId;
	protected AbsMenuBridge(int id){
		mId = id;
	}
	
	public abstract void onCreateOptionItem(Menu menu);
	
	public abstract void onPrepareOptionItem(Menu menu);

	protected IObservable<?> getObservableFromStatement
		(Context context, AttributeSet attributes, 
				String attrName, Object model){
		return getObservableFromStatement(context, attributes, attrName, model, null);
	}
	
	protected IObservable<?> getObservableFromStatement
		(Context context, AttributeSet attributes, 
				String attrName, Object model, IMenuItemChangedCallback callback){
		String attrValue = attributes.getAttributeValue(Binder.BINDING_NAMESPACE, attrName);
		if (attrValue!=null){
			IObservable<?> obs;
			try {
				obs = Binder.getSyntaxResolver().constructObservableFromStatement(context, attrValue, model);
			} catch (SyntaxResolveException e) {
				BindingLog.exception("AbsMenuBridge.getObservableFromStatement", e);
				return null;
			}
			if (callback!=null && !(obs instanceof ConstantObservable) && obs!=null){
				if (observer==null)
					observer = new OptionsItemObserver(callback);
				obs.subscribe(observer);
			}
				
			return obs;
		}
		return null;
	}

	public abstract boolean onOptionsItemSelected(MenuItem item);
	
	private OptionsItemObserver observer;
	private class OptionsItemObserver implements Observer{
		private IMenuItemChangedCallback mCallback;
		
		public OptionsItemObserver(IMenuItemChangedCallback callback){
			mCallback = callback;
		}
		
		@Override
		public void onPropertyChanged(IObservable<?> prop,
				Collection<Object> initiators) {
			if (initiators.contains(AbsMenuBridge.this)) return;
			
			initiators.add(AbsMenuBridge.this);
			mCallback.onItemChanged(prop, AbsMenuBridge.this);
		}
	};
}
