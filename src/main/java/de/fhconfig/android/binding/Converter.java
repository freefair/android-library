package de.fhconfig.android.binding;

import android.content.Context;

/**
 * Base class for All Converters 'function' in XML Markup
 *
 * @param <T>
 * @author andy
 */
public abstract class Converter<T> extends TwoWayDependentObservable<T> {
	private Context mContext;

	public Converter(Class<T> type, IObservable<?>[] dependents) {
		super(type, dependents);
	}

	@Override
	public boolean ConvertBack(Object value, Object[] outResult) {
		return false;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context context) {
		mContext = context;
	}
}
