package de.fhconfig.android.binding;

import java.util.ArrayList;
import java.util.Collection;

import de.fhconfig.android.binding.utility.WeakList;

public class Observable<T> implements IObservable<T> {
	private final Class<T> mType;
	private WeakList<Observer> observers = new WeakList<>();
	private T mValue;
	public Observable(Class<T> type) {
		mType = type;
	}

	public Observable(Class<T> type, T initValue) {
		this(type);
		mValue = initValue;
	}

	@Override
	public String toString() {
		if (!isNull())
			return mValue.toString();
		return "null";
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#subscribe(de.fhconfig.android.binding.Observer)
	 */
	public void subscribe(Observer o) {
		observers.add(o);
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#unsubscribe(de.fhconfig.android.binding.Observer)
	 */
	public void unsubscribe(Observer o) {
		observers.remove(o);
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#notifyChanged(java.lang.Object)
	 */
	public final void notifyChanged(Object initiator) {
		ArrayList<Object> initiators = new ArrayList<>();
		initiators.add(initiator);
		this.notifyChanged(initiators);
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#notifyChanged(java.util.AbstractCollection)
	 */
	public final void notifyChanged(Collection<Object> initiators) {
		initiators.add(this);
		for (Object o : observers.toArray()) {
			if (initiators.contains(o)) continue;
			((Observer) o).onPropertyChanged(this, initiators);
		}
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#notifyChanged()
	 */
	public final void notifyChanged() {
		ArrayList<Object> initiators = new ArrayList<>();
		notifyChanged(initiators);
	}

	/* (non-Javadoc)
	 * @see de.fhconfig.android.binding.IObservable#set(T, java.util.AbstractCollection)
	 */
	public final void set(T newValue, Collection<Object> initiators) {
		if (initiators.contains(this)) return;
		doSetValue(newValue, initiators);
		initiators.add(this);
		notifyChanged(initiators);
	}

	// Intenral use only. 
	public void _setObject(Object newValue, Collection<Object> initiators) {
		try {
			T value = this.getType().cast(newValue);
			if (value == null) return;
			this.set(value, initiators);
		} catch (ClassCastException e) {
			BindingLog.warning("Observable._setObject",
					String.format("Failed to assign value: '%s' to observable of type: '%s', type mismatch",
							newValue, this.getType()));
		}
	}

	public final void set(T newValue) {
		doSetValue(newValue, new ArrayList<>());
		notifyChanged(this);
	}

	protected void doSetValue(T newValue, Collection<Object> initiators) {
		mValue = newValue;
	}

	protected final void setWithoutNotify(T newValue) {
		mValue = newValue;
	}

	public T get() {
		return mValue;
	}

	public final Class<T> getType() {
		return mType;
	}

	public Observer[] getAllObservers() {
		return observers.toItemArray(new Observer[0]);
	}

	@Override
	public boolean isNull() {
		return mValue == null;
	}
}
