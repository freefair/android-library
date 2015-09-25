package de.fhconfig.android.library.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.fhconfig.android.library.ui.fragments.SimplePageFragment;
import de.fhconfig.android.library.viewmodels.Page;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {
	private ObservableArrayList<Page> list;

	public SimplePagerAdapter(ViewPager pager, ObservableArrayList<Page> list) {
		super(((AppCompatActivity)pager.getContext()).getSupportFragmentManager());
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Fragment getItem(int position) {
		return new SimplePageFragment(list.get(position));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return list.get(position).title.get();
	}

	public void updateList(ObservableArrayList<Page> list) {
		if(this.list != list) {
			this.list = list;
			this.list.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
				@Override
				public void onChanged(ObservableList sender) {

				}

				@Override
				public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
					SimplePagerAdapter.this.notifyDataSetChanged();
				}

				@Override
				public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
					SimplePagerAdapter.this.notifyDataSetChanged();
				}

				@Override
				public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
					SimplePagerAdapter.this.notifyDataSetChanged();
				}

				@Override
				public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
					SimplePagerAdapter.this.notifyDataSetChanged();
				}
			});
			this.notifyDataSetChanged();
		}
	}
}
