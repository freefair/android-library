package de.fhconfig.android.library;

import java.util.Set;

/**
 * Created by larsgrefer on 01.02.15.
 */
public interface MultiListenerHost<T> {

	public Set<T> getListeners();

	public void addListener(T listener);

	public boolean removeListener(T listener);
}
