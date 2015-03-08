package de.fhconfig.android.binding.converters;

import android.view.View;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class GO_UP extends Converter<Command> {
	public GO_UP(IObservable<?>[] dependents) {
		super(Command.class, dependents);
	}

	@Override
	public Command calculateValue(Object... args) throws Exception {
		return new Command() {
			@Override
			public void Invoke(View view, Object... args) {
				((BindableActionBar) view).getActivity().finish();
			}
		};
	}
}
