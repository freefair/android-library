package gueei.binding.converters;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import gueei.binding.Converter;
import gueei.binding.DynamicObject;
import gueei.binding.IObservable;
import gueei.binding.collections.ArrayListObservable;

public class BARDATA extends Converter<BarData> {
    public BARDATA(IObservable<?>[] dependents) {
        super(BarData.class, dependents);
    }

    @Override
    public BarData calculateValue(Object... objects) throws Exception {
        DynamicObject object = (DynamicObject) objects[0];

        int count = -1;
        if (object.observableExists("dataSetCount")) {
            IObservable<Integer> dataSetCount = (IObservable<Integer>) object.getObservableByName("dataSetCount");
            count = dataSetCount.get();
        }

        ArrayList<ArrayList<BarEntry>> data = new ArrayList<>();
        for (int i = 0; i < (count==-1?Integer.MAX_VALUE:count); i++)
        {
            if(!object.observableExists("dataSet" + i)) break;

            ArrayListObservable<Float> dataSet = (ArrayListObservable<Float>) object.getObservableByName("dataSet" + i);
            data.add(new ArrayList<>(convertDataSet(dataSet)));
        }

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        ArrayListObservable<String> names = null;
        if(object.observableExists("names"))
        {
            names = (ArrayListObservable<String>) object.getObservableByName("names");
        }
        for(int i = 0; i < data.size(); i++)
        {
            String name = (i+1) + "";
            if(names != null)
                name = names.get(i);
            dataSets.add(new BarDataSet(data.get(i), name));
        }

        return new BarData(new ArrayList<>((ArrayListObservable<String>)object.getObservableByName("xValues")), dataSets);
    }
    private ArrayList<BarEntry> convertDataSet(ArrayListObservable<Float> data)
    {
        ArrayList<BarEntry> result = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
        {
            result.add(new BarEntry(data.get(i), i));
        }

        return result;
    }
}
