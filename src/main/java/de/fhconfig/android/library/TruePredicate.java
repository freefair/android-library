package de.fhconfig.android.library;

import com.google.common.base.Predicate;

/**
 * Created by larsgrefer on 16.03.15.
 */
public class TruePredicate<T> implements Predicate<T> {
	@Override
	public boolean apply(T input) {
		return true;
	}
}
