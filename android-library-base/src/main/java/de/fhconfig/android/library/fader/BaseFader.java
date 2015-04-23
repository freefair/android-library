package de.fhconfig.android.library.fader;

import com.google.common.base.MoreObjects;

import static com.google.common.base.MoreObjects.ToStringHelper;

/**
 * Created by larsgrefer on 02.02.15.
 */
public abstract class BaseFader<T> implements Fader<T> {

	protected T from;
	protected T to;

	@Override
	public T getFrom() {
		return from;
	}

	@Override
	public void setFrom(T from) {
		this.from = from;
		setBounds(from, to);
	}

	@Override
	public T getTo() {
		return to;
	}

	@Override
	public void setTo(T to) {
		this.to = to;
		setBounds(from, to);
	}

	protected ToStringHelper getToStringHelper(){
		return MoreObjects.toStringHelper(this)
					   .add("from", from)
					   .add("to", to);
	}

	@Override
	public String toString() {
		return getToStringHelper().toString();
	}
}
