package io.freefair.android.easing.numeric;

import io.freefair.android.easing.Easing;

public class LinearEasing implements Easing<Double> {
	@Override
	public Double calculate(double time, Double start, Double delta, double totalTime) {
		return delta*time/totalTime + start;
	}
}
