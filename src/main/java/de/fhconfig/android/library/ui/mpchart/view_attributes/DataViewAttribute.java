package de.fhconfig.android.library.ui.mpchart.view_attributes;

import com.github.mikephil.charting.data.BarData;

import de.fhconfig.android.library.ui.mpchart.BarChart;
import de.fhconfig.android.binding.ViewAttribute;

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
