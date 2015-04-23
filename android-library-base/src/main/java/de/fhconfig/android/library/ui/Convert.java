package de.fhconfig.android.library.ui;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by larsgrefer on 04.02.15.
 */
public class Convert {

	Resources resources;

	public Convert(Resources resources){
		this.resources = resources;
	}

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
