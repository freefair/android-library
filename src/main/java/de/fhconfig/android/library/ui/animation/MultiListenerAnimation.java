package de.fhconfig.android.library.ui.animation;

import android.view.animation.Animation;

import java.util.Set;

import de.fhconfig.android.library.MultiListenerHelper;
import de.fhconfig.android.library.MultiListenerHost;

/**
 * Created by larsgrefer on 01.02.15.
 */
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
	public boolean removeListener(AnimationListener listener) {
		return multiListenerHelper.removeListener(listener);
	}
}
