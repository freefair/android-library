package de.fhconfig.android.binding.menu;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Hashtable;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.labs.EventAggregator;

// Each OptionsMenuBinder correspond to one AbsMenuBridge xml. 
// Instance should be kept by the activity
// Deprecated, replacing by BindableOptionsMenu as it is more general 
@Deprecated
public class OptionsMenuBinder implements IMenuItemChangedCallback {
	private final int mMenuResId;
	private boolean firstCreate = true;
	private Hashtable<Integer, AbsMenuBridge> items =
			new Hashtable<>();

	private WeakReference<Activity> mActivity;

	public OptionsMenuBinder(int menuResId) {
		mMenuResId = menuResId;
	}

	// Called by owner activity
	public boolean onCreateOptionsMenu(Activity activity, Menu menu, Object model) {
		mActivity = new WeakReference<>(activity);
		// First inflate the menu - default action
		activity.getMenuInflater().inflate(mMenuResId, menu);

		if (firstCreate) {
			// Now, parse the menu
			XmlResourceParser parser = activity.getResources().getXml(mMenuResId);
			try {
				int eventType = parser.getEventType();
				while (eventType != XmlResourceParser.END_DOCUMENT) {
					if (eventType == XmlResourceParser.START_TAG) {
						int id = parser.getAttributeResourceValue(Binder.ANDROID_NAMESPACE, "id", -1);
						String nodeName = parser.getName();
						if (id > 0) {
							AttributeSet attrs = Xml.asAttributeSet(parser);
							AbsMenuBridge item =
									AbsMenuBridge.create(nodeName, id, attrs, activity, model, null);
							if (item != null) {
								items.put(id, item);
							}
						}
					}
					eventType = parser.next();
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			firstCreate = false;
		}

		for (AbsMenuBridge item : items.values()) {
			item.onCreateOptionItem(menu);
		}

		return true;
	}

	public boolean onPrepareOptionsMenu(Activity activity, Menu menu) {
		for (AbsMenuBridge item : items.values()) {
			item.onPrepareOptionItem(menu);
		}
		return true;
	}

	public boolean onOptionsItemSelected(Activity activity, MenuItem mi) {
		AbsMenuBridge item = items.get(mi.getItemId());
		if (item != null) {
			return item.onOptionsItemSelected(mi);
		}
		return false;
	}

	@Override
	public void onItemChanged(IObservable<?> prop, AbsMenuBridge item) {
		if (mActivity != null && mActivity.get() != null)
			EventAggregator.getInstance(mActivity.get()).publish("invalidateOptionsMenu()", this, new Bundle());
	}
}