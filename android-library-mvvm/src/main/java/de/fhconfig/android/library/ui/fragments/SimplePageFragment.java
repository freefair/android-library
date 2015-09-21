package de.fhconfig.android.library.ui.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.binding.R;
import de.fhconfig.android.library.BRHelper;
import de.fhconfig.android.library.viewmodels.Page;

@SuppressLint("ValidFragment")
public class SimplePageFragment extends Fragment {
	private Page page;
	private int layout = R.layout.simple_fragment;

	public SimplePageFragment() {
	}

	public SimplePageFragment(Page page, int layout) {
		this.page = page;
		this.layout = layout;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		ViewDataBinding inflate = DataBindingUtil.inflate(inflater, layout, container, container != null);
//		inflate.setVariable(BRHelper.getBrByName("item"), page);
//		return inflate.getRoot();
		return inflater.inflate(R.layout.simple_fragment, container, false);
	}
}