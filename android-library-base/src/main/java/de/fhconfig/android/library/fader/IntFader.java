package de.fhconfig.android.library.fader;

public class IntFader extends BaseFader<Integer> {

	int delta;

	public IntFader(int from, int to) {
		setBounds(from, to);
	}

	@Override
	public void setBounds(Integer from, Integer to) {
		this.from = from;
		this.to = to;
		delta = to - from;
	}

	@Override
	public Integer getValue(double weight) {
		return from + (int) Math.round(delta * weight);
	}
}
