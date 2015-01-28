package de.larsgrefer.android.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Dennis Fricke on 28.01.2015.
 */
public class AttributeHelper
{
    public static int getPixels(Context context, int attrId)
    {
        int[] attrs = new int[] { attrId };
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int result = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return result;
    }

    public static Point getDisplaySize(Context context)
    {
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
