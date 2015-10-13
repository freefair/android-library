package io.freefair.android.fader;

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
}
