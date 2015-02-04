package de.larsgrefer.android.library.fader;

/**
 * Created by larsgrefer on 02.02.15.
 */
public interface Fader<T> {

	public void setBounds(T from, T to);

	public void setFrom(T from);

	public T getFrom();

	public void setTo(T to);

	public T getTo();

	public T getValue(double weight);
}
