package de.larsgrefer.android.library.fader;

import android.graphics.Color;

import com.google.common.base.MoreObjects;

import static com.google.common.base.MoreObjects.*;

/**
 * Created by larsgrefer on 30.01.15.
 */
public class ColorFader extends BaseFader<Integer> {

	protected IntFader redFader, greenFader, blueFader, alphaFader;

	public ColorFader(int from, int to) {
		this.from = from;
		this.to = to;
		setBounds(from, to);
	}

	@Override
	public void setBounds(Integer from, Integer to) {
		redFader = new IntFader(Color.red(from), Color.red(to));
		greenFader = new IntFader(Color.green(from), Color.green(to));
		blueFader = new IntFader(Color.blue(from), Color.blue(to));
		alphaFader = new IntFader(Color.alpha(from), Color.alpha(to));
	}

	@Override
	public Integer getValue(double weight) {
		return Color.argb(alphaFader.getValue(weight),
								 redFader.getValue(weight),
								 greenFader.getValue(weight),
								 blueFader.getValue(weight)
		);
	}

	@Override
	protected ToStringHelper getToStringHelper() {
		return super.getToStringHelper()
					   .add("redFader", redFader)
					   .add("greenFader", greenFader)
					   .add("blueFader", blueFader)
					   .add("alphaFader", alphaFader);
	}
}
