package de.fhconfig.android.library.ui.animation;

import android.view.animation.Animation;

import java.util.Collection;
import java.util.Set;

import de.fhconfig.android.library.MultiListenerHelper;
import de.fhconfig.android.library.MultiListenerHost;

public class MultiListenerAnimation extends Animation implements MultiListenerHost<Animation.AnimationListener> {

	MultiListenerHelper<AnimationListener> multiListenerHelper;

	public MultiListenerAnimation(){
		super();
		multiListenerHelper = new MultiListenerHelper<>(AnimationListener.class);
	}

	@Override
	public void setAnimationListener(AnimationListener listener) {
		super.setAnimationListener(multiListenerHelper.getProxyListener());
		multiListenerHelper.addListener(listener);
	}

	@Override
	public Set<AnimationListener> getListeners() {
		return multiListenerHelper.getListeners();
	}

	@Override
	public void addListener(AnimationListener listener) {
		super.setAnimationListener(multiListenerHelper.getProxyListener());
		multiListenerHelper.addListener(listener);
	}

	@Override
	public void addListeners(AnimationListener... listeners) {
		multiListenerHelper.addListeners(listeners);
	}

	@Override
	public void addListeners(Collection<AnimationListener> listeners) {
		multiListenerHelper.addListeners(listeners);
	}

	@Override
	public boolean removeListener(AnimationListener listener) {
		return multiListenerHelper.removeListener(listener);
	}
}
