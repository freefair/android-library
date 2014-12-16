package gueei.binding.converters;

import android.view.View;
import gueei.binding.Command;
import gueei.binding.Converter;
import gueei.binding.IObservable;
import gueei.binding.v30.actionbar.BindableActionBar;

public class GO_UP extends Converter<Command> {
	public GO_UP(IObservable<?>[] dependents) {
		super(Command.class, dependents);
	}

	@Override
	public Command calculateValue(Object... args) throws Exception {
		return new Command() {
			@Override
			public void Invoke(View view, Object... args) {
				((BindableActionBar)view).getActivity().finish();
			}
		};
	}
}
