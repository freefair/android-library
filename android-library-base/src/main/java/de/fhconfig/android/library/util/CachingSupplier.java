package de.fhconfig.android.library.util;

/**
 * Created by larsgrefer on 23.06.15.
 */
public abstract class CachingSupplier<T> implements Supplier<T> {

	private T object;

	@Override
	public T get() {
		if(object == null)
			object = create();
		return object;
	}

	protected abstract T create();

	public static <X> CachingSupplier<X> of(final Supplier<X> baseSupplier){
		return new CachingSupplier<X>() {
			@Override
			protected X create() {
				return baseSupplier.get();
			}
		};
	}
}
