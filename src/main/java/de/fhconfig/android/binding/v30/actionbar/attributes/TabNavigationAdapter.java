package de.fhconfig.android.binding.v30.actionbar.attributes;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.widget.Adapter;
import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.exception.AttributeNotDefinedException;
import de.fhconfig.android.binding.v30.BinderV30;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

import java.util.ArrayList;

public class TabNavigationAdapter extends ViewAttribute<BindableActionBar, Adapter> {

	public TabNavigationAdapter(BindableActionBar view) {
		super(Adapter.class, view, "TabNavigationAdapter");
	}

	private Adapter mAdapter;
	private ActionBarTabAdapterBridge mBridge;
	
	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue instanceof Adapter){
			mAdapter = (Adapter)newValue;
			setupTab();
		}
	}

	/** Setup the tab for the action bar
	 * 
	 */
	private void setupTab() {
		ActionBar ab = getView().getSupportActionBar();
		mBridge = new ActionBarTabAdapterBridge(mAdapter);
		mBridge.rebind(ab);
	}

	@Override
	public Adapter get() {
		return mAdapter;
	}
	
	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		if (Adapter.class.isAssignableFrom(type)){
			return BindingType.OneWay;
		}
		return BindingType.NoBinding;
	}
	
	private class ActionBarTabAdapterBridge implements TabListener{
		private final Adapter mAdapter;
		public ActionBarTabAdapterBridge(Adapter adapter){
			mAdapter = adapter;
		}
		
		public void rebind(ActionBar bar){
			// TODO: Check existing item
			int count = mAdapter.getCount();
			bar.removeAllTabs();
			
			for(int i=0; i<count; i++){
				ActionBar.Tab tab = bar.newTab();
				tab.setCustomView(mAdapter.getView(i, null, null));
				tab.setTabListener(this);
				bar.addTab(tab, i);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			int pos = tab.getPosition();
			ArrayList<Object> initiators = new ArrayList<Object>();
			initiators.add(TabNavigationAdapter.this);
			try {
				TabNavigationSelectedItem item = ((TabNavigationSelectedItem)BinderV30
	            	.getAttributeForView(getView(), "tabNavigationSelectedItem"));
				
				item.set(mAdapter.getItem(pos), initiators);
	            
	            TabNavigationOnItemSelected selected = 
	            		(TabNavigationOnItemSelected)BinderV30
	            			.getAttributeForView(getView(), "tabNavigationOnItemSelected");
	            
	            selected.invokeCommand(getView(), tab, ft);
            } catch (AttributeNotDefinedException e) {
	            e.printStackTrace();
            }
        }

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	        
        }
	}
}
