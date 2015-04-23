package de.fhconfig.android.binding.v30.actionbar.attributes;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.widget.Adapter;
import android.widget.SpinnerAdapter;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class ListNavigationAdapter extends ViewAttribute<BindableActionBar, SpinnerAdapter> {

	private SpinnerAdapter mAdapter;

	public ListNavigationAdapter(BindableActionBar view) {
		super(SpinnerAdapter.class, view, "ListNavigationAdapter");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (newValue instanceof SpinnerAdapter) {
			ActionBar bar = getHost().getSupportActionBar();
			bar.setListNavigationCallbacks((SpinnerAdapter) newValue,
					new NavigationListener());
			mAdapter = (SpinnerAdapter) newValue;
		}
	}

	@Override
	public SpinnerAdapter get() {
		return mAdapter;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		if (SpinnerAdapter.class.isAssignableFrom(type)) {
			return BindingType.TwoWay;
		}
		if (Adapter.class.isAssignableFrom(type)) {
			return BindingType.OneWay;
		}
		return BindingType.NoBinding;
	}

	public boolean onNavigationItemSelected(int position, long itemId) {
		try {
			ListNavigationSelectedItem selectedItem =
					(ListNavigationSelectedItem) Binder.getAttributeForView(getView(), "ListNavigationSelectedItem");
			selectedItem.set(mAdapter.getItem(position));

			ListNavigationOnItemSelected onItemSelected =
					(ListNavigationOnItemSelected) Binder.getAttributeForView(getView(), "ListNavigationOnItemSelected");
			onItemSelected.invokeCommand(getView(), position, itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private class NavigationListener implements OnNavigationListener {
		public boolean onNavigationItemSelected(
				int itemPosition, long itemId) {
			try {
				ListNavigationSelectedItem selectedItem =
						(ListNavigationSelectedItem) Binder.getAttributeForView(getView(), "listNavigationSelectedItem");
				selectedItem.set(mAdapter.getItem(itemPosition));

				ListNavigationOnItemSelected onItemSelected =
						(ListNavigationOnItemSelected) Binder.getAttributeForView(getView(), "listNavigationOnItemSelected");
				onItemSelected.invokeCommand(getView(), itemPosition, itemId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
