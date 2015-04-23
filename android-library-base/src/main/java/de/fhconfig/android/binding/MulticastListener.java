package de.fhconfig.android.binding;

import java.util.ArrayList;

public abstract class MulticastListener<Th, T> {
	protected ArrayList<T> listeners = new ArrayList<>(0);
	private boolean mBroadcasting = true;

	public abstract void registerToHost(Th host);

	public void removeListener(T listener) {
		listeners.remove(listener);
	}

	public void register(T listener) {
		listeners.add(listener);
	}

	public void registerWithHighPriority(T listener) {
		listeners.add(0, listener);
	}

	public void nextActionIsNotFromUser() {
		mBroadcasting = false;
	}

	protected boolean isFromUser() {
		return mBroadcasting;
	}

	protected void clearBroadcastState() {
		mBroadcasting = true;
	}
}
