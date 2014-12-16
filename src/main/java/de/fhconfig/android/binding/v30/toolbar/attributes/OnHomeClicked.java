package de.fhconfig.android.binding.v30.toolbar.attributes;

import android.os.Bundle;
import de.fhconfig.android.binding.labs.EventAggregator;
import de.fhconfig.android.binding.labs.EventSubscriber;
import de.fhconfig.android.binding.v30.toolbar.BindableToolbar;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

public class OnHomeClicked extends ViewEventAttribute<BindableToolbar> {
	public OnHomeClicked(BindableToolbar view) {
		super(view, "onHomeClicked");
	}

	@Override
	protected void registerToListener(BindableToolbar view) {
		EventAggregator.getInstance(view.getActivity()).subscribe("Clicked(android.R.id.home)",
				new EventSubscriber(){
					public void onEventTriggered(String eventName,
							Object publisher, Bundle data) {
						OnHomeClicked.this.invokeCommand(getView());
					}
		});
	}
}
