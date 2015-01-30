package de.larsgrefer.android.library.ui;

import android.graphics.Color;

/**
 * Created by larsgrefer on 30.01.15.
 */
public class ColorFader {

	int redFrom, greenFrom, blueFrom, alphaFrom;
	int redDelta, greenDelta, blueDelta, alphaDelta;

	int from, to;

	public ColorFader(int from, int to) {
		setColors(from, to);
	}

	public int getColor(float weight) {
		return Color.argb(alphaFrom + (int) (alphaDelta * weight),
								 redFrom + (int) (redDelta * weight),
								 greenFrom + (int) (greenDelta * weight),
								 blueFrom + (int) (blueDelta * weight));
	}

	public void setColors(int from, int to){
		this.from = from;
		this.to = to;

		redFrom = Color.red(from);
		redDelta = Color.red(to) - redFrom;

		greenFrom = Color.green(from);
		greenDelta = Color.green(to) - greenFrom;

		blueFrom = Color.blue(from);
		blueDelta = Color.blue(to) - blueFrom;

		alphaFrom = Color.alpha(from);
		alphaDelta = Color.alpha(to) - alphaFrom;
	}

	public void setFrom(int from){
		setColors(from, to);
	}

	public int getFrom(){
		return from;
	}

	public void setTo(int to){
		setColors(from, to);
	}

	public int getTo(){
		return to;
	}

}
