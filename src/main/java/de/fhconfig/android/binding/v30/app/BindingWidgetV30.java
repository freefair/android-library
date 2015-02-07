package de.fhconfig.android.binding.v30.app;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import de.fhconfig.android.binding.AttributeBinder;
import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.IObservableCollection;
import de.fhconfig.android.binding.menu.MenuItemViemodel;
import de.fhconfig.android.binding.v30.widget.PopupMenuBinderV30;

import static android.os.Build.VERSION_CODES.HONEYCOMB;

@TargetApi(HONEYCOMB)
public class BindingWidgetV30 {
	
	public static PopupMenu createAndBindPopupMenu(View view, int menuId, Object menuViewModel) {
		PopupMenu popup = new PopupMenu(view.getContext(), view);		
		
		PopupMenuBinderV30 menuBinder = new PopupMenuBinderV30();
		menuBinder.createAndBindPopupMenu(view, popup, menuId, menuViewModel);
		return popup;
	}
	
	public static PopupMenu createAndBindPopupMenu(View view, IObservableCollection<MenuItemViemodel> items) {
		PopupMenu popup = new PopupMenu(view.getContext(), view);		
		
		PopupMenuBinderV30 menuBinder = new PopupMenuBinderV30();
		menuBinder.bindPopupMenu(popup, items);
		return popup;
	}

	public static Dialog createAndBindDialog(Context context, int layoutId, Object contentViewModel) {
		return createAndBindDialog(context, 0, layoutId, contentViewModel);
	}
	
	public static Dialog createAndBindDialog(Context context, int theme, int layoutId, Object contentViewModel) {
		Dialog dialog = new Dialog(context, theme);
		InflateResult result = Binder.inflateView(context, layoutId, null, false);
		dialog.setContentView(result.rootView);
		for(View v: result.processedViews){
			AttributeBinder.getInstance().bindView(context, v, contentViewModel);
		}			
        return dialog;                 
	}	
}
