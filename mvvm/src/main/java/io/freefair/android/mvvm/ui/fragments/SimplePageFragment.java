package io.freefair.android.mvvm.ui.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.freefair.android.mvvm.BRHelper;
import io.freefair.android.mvvm.viewmodels.Page;

@SuppressLint("ValidFragment")
public class SimplePageFragment extends Fragment {
    private Page page;

    public SimplePageFragment() {
    }

    public SimplePageFragment(Page page) {
        this.page = page;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, page.layout.get(), container, false);
        inflate.setVariable(BRHelper.getBrByName("item"), page.model.get());
        return inflate.getRoot();
    }
}