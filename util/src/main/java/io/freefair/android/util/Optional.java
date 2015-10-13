package io.freefair.android.util;

/**
 * Created by larsgrefer on 11.10.15.
 */
public class Optional<T> {

	final T object;

	private Optional(T obj) {
		object = obj;
	}

	public boolean isPresent() {
		return object != null;
	}

	public T get() {
		if (isPresent())
			return object;
		else {
			throw new IllegalStateException("This optional is empty");
		}
	}

	public T orNull() {
		return object;
	}

	public T orElse(T alternative) {
		return isPresent() ? object : alternative;
	}

	public <V> Optional<V> map(Function<? super T, V> function) {
		if (function == null)
			throw new IllegalArgumentException("function was null");
		else {
			if (isPresent()) {
				return Optional.ofNullable(function.apply(get()));
			} else {
				return Optional.empty();
			}
		}
	}

	private final static Optional<?> emptyOptional = new Optional<>(null);

	public static <X> Optional<X> of(X object) {
		if (object == null)
			throw new IllegalArgumentException("object was null");
		else
			return new Optional<>(object);
	}


	public static <X> Optional<X> ofNullable(X object) {
		if (object == null)
			return Optional.empty();
		else
			return new Optional<>(object);
	}

	@SuppressWarnings("unchecked")
	public static <X> Optional<X> empty() {
		return (Optional<X>) emptyOptional;
	}
}
