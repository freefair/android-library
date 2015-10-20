package io.freefair.android.injection.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.InjectorProvider;
import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.injection.annotation.XmlLayout;
import io.freefair.android.injection.annotation.XmlMenu;
import io.freefair.android.injection.exceptions.ViewIdNotFoundException;
import io.freefair.android.injection.platform.FragmentInjector;
import io.freefair.android.injection.platform.IViewFinder;
import io.freefair.android.util.Optional;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class InjectionFragment extends Fragment implements InjectorProvider{

	@Inject Optional<XmlMenu> xmlMenuAnnotation;
	@Inject Optional<XmlLayout> xmlLayoutAnnotation;

	FragmentInjector injector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Injector parentInjector = null;
		if(getActivity() instanceof InjectorProvider) {
			parentInjector = ((InjectorProvider) getActivity()).getInjector();
		}
		injector = new FragmentInjector(this, parentInjector);
		injector.inject(this);
		if(xmlMenuAnnotation.isPresent()){
			setHasOptionsMenu(true);
		}

		injector.injectResources();
		injector.injectAttributes();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (xmlLayoutAnnotation.isPresent()) {
			return inflater.inflate(xmlLayoutAnnotation.get().value(), container, false);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
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
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (xmlMenuAnnotation.isPresent()) {
			inflater.inflate(xmlMenuAnnotation.get().value(), menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		injector.injectResources();
		injector.injectAttributes();
	}

	@Override
	public Injector getInjector() {
		return null;
	}
}
