package de.larsgrefer.android.library.ui.animation;

import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import de.larsgrefer.android.library.fader.Fader;
import de.larsgrefer.android.library.fader.FloatFader;

/**
 * Created by larsgrefer on 02.02.15.
 */
public class TextSizeAnimation extends ViewAnimation<TextView> {

	protected Fader<Float> textSizeFader;

	public TextSizeAnimation(TextView view, Fader<Float> textSizeFader){
		super();
		setView(view);
		setTextSizeFader(textSizeFader);
	}

	public TextSizeAnimation(TextView view, float from, float to){
		this(view, new FloatFader(from, to));
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSizeFader().getValue(interpolatedTime));
	}


	public Fader<Float> getTextSizeFader() {
		return textSizeFader;
	}

	public void setTextSizeFader(Fader<Float> textSizeFader) {
		this.textSizeFader = textSizeFader;
	}

	private class TextSizeAnimationListener implements AnimationListener{

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFader.getValue(0));
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	}
}
