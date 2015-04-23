package de.fhconfig.android.binding;

import android.view.View;

import de.fhconfig.android.binding.utility.WeakList;

public abstract class Command extends Observable<Command> {
	WeakList<CommandListener> listeners = new WeakList<>();

	public Command() {
		super(Command.class);
	}

	@Override
	public Command get() {
		return this;
	}

	public void InvokeCommand(View view, Object... args) {
		for (Object l : listeners.toArray()) {
			((CommandListener) l).onBeforeInvoke();
		}
		Invoke(view, args);
		for (Object l : listeners.toArray()) {
			((CommandListener) l).onAfterInvoke();
		}
	}

	public abstract void Invoke(View view, Object... args);

	public void addCommandListener(CommandListener l) {
		listeners.add(l);
	}

	public void removeCommandListener(CommandListener l) {
		listeners.remove(l);
	}

	public interface CommandListener {
		public void onBeforeInvoke();

		public void onAfterInvoke();
	}
}
