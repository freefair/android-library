package de.fhconfig.android.binding.viewAttributes.ratingBar;

import android.widget.RatingBar;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnRatingBarChangeListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * On Rating Changed View Event. Fires when Rating Bar's rating changed
 *
 * @author andy
 * @name onRatingChanged
 * @widget RatingBar
 * @type Command
 * @accepts Command
 * @category simple
 * @related http://developer.android.com/reference/android/widget/RatingBar.OnRatingBarChangeListener.html
 * @related http://developer.android.com/reference/android/widget/RatingBar.html
 */
public class OnRatingChangedViewEvent extends ViewEventAttribute<RatingBar>
		implements RatingBar.OnRatingBarChangeListener {

	public OnRatingChangedViewEvent(RatingBar view) {
		super(view, "onRatingChanged");
	}

	public void onRatingChanged(RatingBar ratingBar, float rating,
	                            boolean fromUser) {
		if (fromUser)
			this.invokeCommand(ratingBar, rating, fromUser);
	}

	@Override
	protected void registerToListener(RatingBar view) {
		Binder.getMulticastListenerForView(view, OnRatingBarChangeListenerMulticast.class).register(this);
	}
}
