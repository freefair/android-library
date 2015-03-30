package de.fhconfig.android.library.predicate;

/**
 * Created by larsgrefer on 31.03.15.
 */
public class FalsePredicate extends Predicate<Object> {

	private FalsePredicate(){}

	private static FalsePredicate instance;

	public static FalsePredicate getInstance() {
		if(instance == null){
			instance = new FalsePredicate();
		}
		return instance;
	}

	@Override
	public boolean apply(Object input) {
		return false;
	}

	@Override
	public FalsePredicate and(com.google.common.base.Predicate other) {
		return this;
	}

	@Override
	public Predicate<Object> or(com.google.common.base.Predicate<? super Object> other) {
		return of(other);
	}

	@Override
	public TruePredicate not() {
		return always();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof FalsePredicate || super.equals(o);
	}
}
