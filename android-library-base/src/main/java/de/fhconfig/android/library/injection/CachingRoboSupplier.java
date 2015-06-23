package de.fhconfig.android.library.injection;

/**
 * Created by larsgrefer on 23.06.15.
 */
public abstract class CachingRoboSupplier<T> implements IRoboSupplier<T> {

	private T object;

	@Override
	public T get() {
		if(object == null)
			object = create();
		return object;
	}

	protected abstract T create();
}
