package de.fhconfig.android.binding.menu.viewAttributes;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.menu.BindableOptionsMenu;

public class DataSource extends ViewAttribute<BindableOptionsMenu, Object> {
	public DataSource(BindableOptionsMenu view) {
		super(Object.class, view, "dataSource");
	}

	private Object mSource = null;
	
	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (mSource==null || !mSource.equals(newValue)){
			mSource = newValue;
			getHost().invalidate();
		}
	}

	@Override
	public Object get() {
		return mSource;
	}
}
