package de.fhconfig.android.binding.v30.viewAttributes.adapterView.viewPager;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;

public class CurrentItemViewAttribute extends ViewAttribute<ViewPager, Integer> implements OnPageChangeListener {

	public CurrentItemViewAttribute
			(ViewPager view) {
		super(Integer.class, view, "currentItem");

		Binder.getMulticastListenerForView(getView(), OnPageChangeListenerMulticast.class)
				.register(this);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue instanceof Integer) {
			getView().setCurrentItem((Integer) newValue);
		}
	}

	@Override
	public Integer get() {
		if (getView() == null) return null;
		return getView().getCurrentItem();
	}

	public void onPageScrollStateChanged(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int arg0) {
		this.notifyChanged(this);
	}
}
