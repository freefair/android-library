package de.larsgrefer.android.library.fader;

import static com.google.common.base.MoreObjects.ToStringHelper;

/**
 * Created by larsgrefer on 02.02.15.
 */
public class FloatFader extends BaseFader<Float> {

	float delta;

	public FloatFader(float from, float to) {
		setBounds(from, to);
	}

	@Override
	public void setBounds(Float from, Float to) {
		this.from = from;
		this.to = to;
		delta = to - from;
	}

	@Override
	public Float getValue(double weight) {
		return from + (float) (delta * weight);
	}

	@Override
	protected ToStringHelper getToStringHelper() {
		return super.getToStringHelper().add("delta", delta);
	}
}
