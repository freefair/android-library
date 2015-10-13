package de.fhconfig.android.library.util;

/**
 * Created by larsgrefer on 11.10.15.
 */
public final class Suppliers {
	private Suppliers(){}

	public static <T> Supplier<T> of(final T object){
		return new Supplier<T>() {
			@Override
			public T get() {
				return object;
			}
		};
	}

	public static <T> Supplier<T> cache(final Supplier<T> supplier){
		return new CachingSupplier<T>() {
			@Override
			protected T create() {
				return supplier.get();
			}
		};
	}
}
