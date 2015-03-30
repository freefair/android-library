package de.fhconfig.android.library.predicate;

/**
 * Created by larsgrefer on 30.03.15.
 */
public class NotPredicate<T> extends Predicate<T> {

	com.google.common.base.Predicate<T> predicate;

	protected NotPredicate(com.google.common.base.Predicate<T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean apply(T input) {
		return !predicate.apply(input);
	}

	@Override
	public Predicate<T> not() {
		return Predicate.of(predicate);
	}
}
