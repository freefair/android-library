package de.fhconfig.android.library.ui.animation;

import android.content.res.ColorStateList;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import de.fhconfig.android.library.fader.ColorFader;

/**
 * Created by larsgrefer on 01.02.15.
 */
public class TextColorAnimation extends ViewAnimation<TextView> {

	ColorFader colorFader;
	ColorStateList colorStateList;

	public TextColorAnimation(TextView view, int colorFrom, int colorTo){
		this(view, new ColorFader(colorFrom, colorTo));
	}

	public TextColorAnimation(final TextView view, ColorFader fader){
		super();
		setView(view);
		this.colorFader = fader;
		addListener(new TextColorAnimationListener());
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		view.setTextColor(colorFader.getValue(interpolatedTime));
	}

	@Override
	public void setView(TextView view) {
		super.setView(view);
		colorStateList = view.getTextColors();
	}

	public ColorFader getColorFader() {
		return colorFader;
	}

	public void setColorFader(ColorFader colorFader) {
		this.colorFader = colorFader;
	}

	private class TextColorAnimationListener implements AnimationListener{

		@Override
		public void onAnimationStart(Animation animation) {
			colorStateList = view.getTextColors();
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view.setTextColor(colorStateList);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	}
}
