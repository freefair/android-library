package de.fhconfig.android.library.predicate;

/**
 * Created by larsgrefer on 30.03.15.
 */
public abstract class Predicate<T> implements com.google.common.base.Predicate<T> {

	public Predicate<T> and(com.google.common.base.Predicate<? super T> other){
		return new AndPredicate<>(this, other);
	}

	public Predicate<T> or(com.google.common.base.Predicate<? super T> other){
		return new OrPredicate<>(this, other);
	}

	public Predicate<T> xor(com.google.common.base.Predicate<? super T> other){
		return new XorPredicate<>(this, other);
	}

	public Predicate<T> not(){
		return new NotPredicate<>(this);
	}

	public static <S> Predicate<S> of(final com.google.common.base.Predicate<? super S> predicate){
		if(predicate instanceof Predicate){
			return (Predicate<S>) predicate;
		}
		return new Predicate<S>() {
			@Override
			public boolean apply(S input) {
				return predicate.apply(input);
			}
		};
	}

	public static TruePredicate always(){
		return TruePredicate.getInstance();
	}

	public static FalsePredicate never(){
		return FalsePredicate.getInstance();
	}
}
