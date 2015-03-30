package de.fhconfig.android.library.predicate;

/**
 * Created by larsgrefer on 16.03.15.
 */
public class TruePredicate extends Predicate<Object> {

	private TruePredicate() {}

	private static TruePredicate instance;

	public static TruePredicate getInstance() {
		if(instance == null){
			instance = new TruePredicate();
		}
		return instance;
	}

	@Override
	public boolean apply(Object input) {
		return true;
	}

	@Override
	public Predicate<Object> and(com.google.common.base.Predicate<? super Object> other) {
		return of(other);
	}

	@Override
	public TruePredicate or(com.google.common.base.Predicate other) {
		return this;
	}

	@Override
	public FalsePredicate not() {
		return never();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof TruePredicate || super.equals(o);
	}
}
