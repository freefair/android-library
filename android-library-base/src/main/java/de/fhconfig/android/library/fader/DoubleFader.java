package de.fhconfig.android.library.fader;

import static com.google.common.base.MoreObjects.ToStringHelper;

/**
 * Created by larsgrefer on 02.02.15.
 */
public class DoubleFader extends BaseFader<Double> {

	double delta;

	public DoubleFader(double from, double to){
		setBounds(from, to);
	}

	@Override
	public void setBounds(Double from, Double to) {
		this.from = from;
		this.to = to;
		delta = to - from;
	}

	@Override
	public Double getValue(double weight) {
		return from + (delta * weight);
	}

	@Override
	protected ToStringHelper getToStringHelper() {
		return super.getToStringHelper().add("delta", delta);
	}
}
