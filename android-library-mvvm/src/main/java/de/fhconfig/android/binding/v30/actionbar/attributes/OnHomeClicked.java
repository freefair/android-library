package de.fhconfig.android.binding.v30.actionbar.attributes;

import android.os.Bundle;

import de.fhconfig.android.binding.labs.EventAggregator;
import de.fhconfig.android.binding.labs.EventSubscriber;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnHomeClicked extends ViewEventAttribute<BindableActionBar> {
	public OnHomeClicked(BindableActionBar view) {
		super(view, "onHomeClicked");
	}

	@Override
	protected void registerToListener(BindableActionBar view) {
		EventAggregator.getInstance(view.getActivity()).subscribe("Clicked(android.R.id.home)",
				new EventSubscriber() {
					public void onEventTriggered(String eventName,
					                             Object publisher, Bundle data) {
						OnHomeClicked.this.invokeCommand(getView());
					}
				});
	}
}
