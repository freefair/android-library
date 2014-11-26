package de.larsgrefer.android.library.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.larsgrefer.android.library.injection.FragmentInjector;
import de.larsgrefer.android.library.injection.IViewFinder;
import de.larsgrefer.android.library.injection.ViewIdNotFoundException;
import de.larsgrefer.android.library.injection.annotation.XmlLayout;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class InjectionFragment extends Fragment {

	FragmentInjector injector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		injector = new FragmentInjector(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Class<? extends InjectionFragment> clazz = this.getClass();
		if(clazz.isAnnotationPresent(XmlLayout.class)){
			XmlLayout xmlLayoutAnnotation = clazz.getAnnotation(XmlLayout.class);
			final View view = inflater.inflate(xmlLayoutAnnotation.id(), container, false);

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
		return super.onCreateView(inflater,container,savedInstanceState);
	}
}
