package de.larsgrefer.android.library.ui.mpchart.view_attributes;

import android.view.View;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineDataSet;

import de.larsgrefer.android.library.ui.mpchart.BarChart;
import gueei.binding.ViewAttribute;

public class DataViewAttribute extends ViewAttribute<BarChart, BarData> {
    public DataViewAttribute(BarChart barChart) {
        super(BarData.class, barChart, "data");
    }

    @Override
    protected void doSetAttributeValue(Object o) {
        if(o instanceof BarData)
        {
            getView().setData((BarData) o);
        }
    }

    @Override
    public BarData get() {
        return getView().getData();
    }
}
