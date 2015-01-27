package de.larsgrefer.android.library.ui.mpchart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.github.mikephil.charting.data.LineData;

import de.larsgrefer.android.library.ui.mpchart.view_attributes.DataViewAttribute;
import gueei.binding.IBindableView;
import gueei.binding.ViewAttribute;

public class BarChart extends com.github.mikephil.charting.charts.BarChart implements IBindableView<BarChart> {
    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ViewAttribute<? extends View, ?> createViewAttribute(String s) {
        if("data".equals(s))
        {
            return new DataViewAttribute(this);
        }
        return null;
    }
}
