package io.freefair.android.ui;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import io.freefair.android.injection.annotation.Inject;

/**
 * Created by larsgrefer on 04.02.15.
 */
public class Converter {

	@Inject
	Resources resources;

	public float pxToDip(float px){
		return pxToDip(px, resources);
	}

	public static float pxToDip(float px, View view){
		return pxToDip(px, view.getResources());
	}

	public static float pxToDip(float px, Resources resources){
		return px / resources.getDisplayMetrics().density;
	}

	public int dipToPx(float dps){
		return dipToPx(dps, resources);
	}

	public static int dipToPx(float dps, View view){
		return dipToPx(dps, view.getResources());
	}

	public static int dipToPx(float dps, Resources resources) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, resources.getDisplayMetrics());
	}
}
