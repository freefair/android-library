package io.freefair.android.mvvm;

import android.app.Activity;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import io.freefair.android.mvvm.adapters.SimpleAdapter;
import io.freefair.android.mvvm.adapters.SimplePagerAdapter;
import io.freefair.android.mvvm.viewmodels.Page;

@SuppressWarnings("unused")
public class Conversions {
    @BindingAdapter({"bind:items", "bind:layout"})
    public static void listViewAdapter(ListView view, ObservableList list, int layout) {
        Object tag = view.getTag(R.id.adapter_id);
        if (tag == null || !(tag instanceof SimpleAdapter)) {
            SimpleAdapter adapter = new SimpleAdapter(view, list, layout);
            view.setTag(R.id.adapter_id, adapter);
            view.setAdapter(adapter);
            tag = adapter;
        }
        SimpleAdapter adapter = (SimpleAdapter) tag;
        adapter.updateList(list);
        adapter.updateLayout(layout);
    }


    @BindingAdapter("bind:colorSchemeColors")
    public static void setRefreshColor(SwipeRefreshLayout view, @ColorRes TypedArray colorRes) {
        int[] res = new int[colorRes.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = colorRes.peekValue(i).data;
        }
        TypedArray typedArray = view.getContext().getTheme().obtainStyledAttributes(res);
        int[] colors = new int[typedArray.length()];
        for (int i = 0; i < res.length; i++) {
            colors[i] = typedArray.getColor(i, -1);
        }
        view.setColorSchemeColors(colors);
        //view.setColorSchemeColors(colorRes);
    }

    @BindingAdapter("bind:drawableLeft")
    public static void setDrawableLeft(TextView view, @DrawableRes int id) {
        view.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
    }


    @BindingAdapter({"bind:items"})
    public static void viewPagerAdapter(ViewPager pager, ObservableArrayList<Page> list) {
        Object tag = pager.getTag(R.id.adapter_id);
        if (tag == null || !(tag instanceof SimplePagerAdapter)) {
            SimplePagerAdapter adapter = new SimplePagerAdapter(pager, list);
            pager.setTag(R.id.adapter_id, adapter);
            pager.setAdapter(adapter);
            tag = adapter;
        }
        SimplePagerAdapter adapter = (SimplePagerAdapter) tag;
        adapter.updateList(list);
    }

    @BindingConversion
    public static String convertDateToString(Date date) {
        return DateFormat.getDateTimeInstance().format(date);
    }

    @BindingAdapter({"bind:tabLayout"})
    public static void setViewPager(ViewPager viewPager, int id) {
        Activity context = (Activity) viewPager.getContext();
        TabLayout viewById = (TabLayout) context.findViewById(id);
        if (viewById == null) throw new RuntimeException("TabLayout with id " + id + " not found");
        viewById.setupWithViewPager(viewPager);
    }
}
