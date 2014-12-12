package de.larsgrefer.android.library.ui.injection;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import de.larsgrefer.android.library.injection.FragmentXmlInjector;
import de.larsgrefer.android.library.injection.IViewFinder;
import de.larsgrefer.android.library.injection.exceptions.ViewIdNotFoundException;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class InjectionFragment extends Fragment {

	FragmentXmlInjector injector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		injector = new FragmentXmlInjector(this);
		if(injector.getMenuId() != 0){
			setHasOptionsMenu(true);
		}

		Application app = getActivity().getApplication();
		if(app instanceof InjectionApplication)
			((InjectionApplication)app).getInjector().inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (injector.getLayoutId() != 0) {
			final View view = inflater.inflate(injector.getLayoutId(), container, false);

			injector.setViewFinder(new IViewFinder() {
				@Override
				public View findViewById(@IdRes int viewId) {
					return view.findViewById(viewId);
				}
			});
			try {
				injector.injectViews();
			} catch (ViewIdNotFoundException e) {
				throw new RuntimeException(e);
			}

			return view;
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (injector.getMenuId() != 0) {
			inflater.inflate(injector.getMenuId(), menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}
}
