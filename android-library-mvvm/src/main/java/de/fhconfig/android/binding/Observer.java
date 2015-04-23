package de.fhconfig.android.binding;

import java.util.Collection;

public interface Observer {
	public void onPropertyChanged(IObservable<?> prop, Collection<Object> initiators);
}
