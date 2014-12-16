package de.fhconfig.android.binding.v30.actionbar;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.ViewAttribute;

import java.lang.ref.WeakReference;

public class BindableActionBar extends View implements IBindableView<BindableActionBar>{

	WeakReference<ActionBarActivity> mActivityRef;
	
	public ActionBar getSupportActionBar(){
		if(mActivityRef==null || mActivityRef.get() == null)
			return null;
		return mActivityRef.get().getSupportActionBar();
	}
	
	public BindableActionBar(ActionBarActivity context) {
		super(context);
		mActivityRef = new WeakReference<ActionBarActivity>(context);
	}
	
	public Activity getActivity(){
		if(mActivityRef==null || mActivityRef.get() == null)
			return null;
		return mActivityRef.get();
	}

	public ViewAttribute<? extends View, ?> createViewAttribute(
			String attributeId) {
		try{
			String capId = attributeId.substring(0, 1).toUpperCase() + attributeId.substring(1);
			String className = "gueei.binding.v30.actionbar.attributes." + capId;
			return (ViewAttribute<?,?>)Class.forName(className)
						.getConstructor(BindableActionBar.class)
						.newInstance((BindableActionBar)this);
		}catch(Exception e){
			BindingLog.warning("ActionBarAttributeBinder", "Attribute not found");
			return null;
		}
	}
}