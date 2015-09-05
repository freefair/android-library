package de.fhconfig.android.library.easing.numeric;

import de.fhconfig.android.library.easing.Easing;

public class LinearEasing implements Easing<Double> {
	@Override
	public Double calculate(double time, Double start, Double delta, double totalTime) {
		return delta*time/totalTime + start;
	}
}
