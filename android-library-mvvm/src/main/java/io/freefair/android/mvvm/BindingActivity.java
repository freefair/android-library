package io.freefair.android.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.freefair.android.mvvm.annotations.BindTo;
import io.freefair.android.mvvm.annotations.Binding;
import io.freefair.android.mvvm.annotations.DrawerToggle;
import io.freefair.android.mvvm.annotations.Event;
import io.freefair.android.mvvm.annotations.Layout;
import io.freefair.android.mvvm.annotations.MenuItemClick;
import io.freefair.android.mvvm.annotations.Toolbar;
import io.freefair.android.mvvm.annotations.specific.EventName;
import io.freefair.android.mvvm.annotations.specific.EventNames;
import io.freefair.android.Logger;
import io.freefair.android.injection.annotation.Inject;
import io.freefair.android.mvvm.ui.GeneralEventListener;
import io.freefair.android.ui.injection.InjectionAppCompatActivity;
import io.freefair.android.util.Function;
import io.freefair.android.util.Optional;

public class BindingActivity extends InjectionAppCompatActivity implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {


	private Map<Integer, Method> menuListeners = new HashMap<>();

	@Inject
	private Optional<Layout> layoutAnnotation;
	@Inject
	private Optional<Toolbar> toolbarAnnotation;
	@Inject
	private Optional<DrawerToggle> drawerToggleAnnotation;

	private Optional<ActionBarDrawerToggle> drawerToggle = Optional.empty();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflateAndBind();
		createEventListener();
	}

	private void createEventListener() {
		class AnnotationHolder {
			Annotation a;
			EventName name;

			public AnnotationHolder(Annotation a, EventName name) {
				this.a = a;
				this.name = name;
			}
		}

		Method[] declaredMethods = this.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			Event annotation = method.getAnnotation(Event.class);
			if (annotation != null) {
				int value = annotation.value();
				String event = annotation.event().getName();
				if (event == null || EventNames.NONE.getName().equals(event)) {
					event = method.getName();
				}
				View view = findViewById(value);
				if(view == null && !annotation.required())
					continue;
				else if (view == null && annotation.required())
					throw new RuntimeException("View with id "  + value +  " not found");
				bindEventToView(method, event, view);
				continue;
			}
			List<AnnotationHolder> collect = new ArrayList<>();
			for (Annotation annot : method.getAnnotations()) {
				AnnotationHolder annotationHolder = new AnnotationHolder(annot, annot.annotationType().getAnnotation(EventName.class));
				if(annotationHolder.name != null)
					collect.add(annotationHolder);
			}

			for (AnnotationHolder a : collect) {
				try {
					Method value = a.a.annotationType().getMethod("value");
					int invoke = (int) value.invoke(a.a);
					String event = a.name.value().getName();
					View viewById = findViewById(invoke);
					if(viewById == null && a.name.required())
						throw new RuntimeException("View with id " + invoke + " not found");
					else if (viewById == null && !a.name.required())
						continue;
					bindEventToView(method, event, viewById);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	private class MethodListener implements GeneralEventListener.EventListener {

		private Method method;

		public MethodListener(Method method) {
			this.method = method;
		}

		@Override
		public void invoke(View sender, Object... args) throws Throwable {
			Class<?>[] parameterTypes = method.getParameterTypes();
			Object[] arg;
			if (parameterTypes.length == args.length)
				arg = args;
			else if (parameterTypes.length == 0)
				arg = new Object[0];
			else {
				List<Object> obj = new ArrayList<>();
				for (Class<?> type :
						parameterTypes) {
					int i = obj.size();
					for (Object a : args) {
						if (a.getClass() == type && !obj.contains(a)) {
							obj.add(a);
							break;
						}
					}
					if (obj.size() == i)
						obj.add(null);
				}
				arg = obj.toArray(new Object[obj.size()]);
			}
			method.invoke(BindingActivity.this, arg);
		}
	}

	private void bindEventToView(Method method, String event, View view) {
		GeneralEventListener byView = GeneralEventListener.getByView(view);
		if(hasListenerForMethod(byView, event, method)) return;
		method.setAccessible(true);
		byView.bindEvent(event, new MethodListener(method));
	}

	private boolean hasListenerForMethod(GeneralEventListener byView, String event, Method method) {
		Map<String, List<GeneralEventListener.EventListener>> events = byView.getListeners();
		String methodName = GeneralEventListener.buildMethodName(event);
		if(!events.containsKey(methodName)) return false;
		List<GeneralEventListener.EventListener> listeners = events.get(methodName);
		for (GeneralEventListener.EventListener listener :
				listeners) {
			if (listener instanceof MethodListener)
			{
				if(((MethodListener) listener).method == method)
					return true;
			}
		}
		return false;
	}

	private void inflateAndBind() {
		try {
			ViewDataBinding binding = DataBindingUtil.setContentView(this, layoutAnnotation.map(new Function<Layout, Integer>() {
				@Override
				public Integer apply(Layout layout) {
					return layout.value();
				}
			}).orElse(-1));
			bindAll(binding);
			binding.addOnRebindCallback(new OnRebindCallback() {
				@Override
				public void onBound(ViewDataBinding binding) {
					try {
						bindAll(binding);
						createEventListener();
					} catch (IllegalAccessException ex) {
						Logger.error(this, "Error while set binding to annotated field", ex);
					} catch (InstantiationException ex) {
						Logger.error(this, "Error while creating view model", ex);
					} catch (NoSuchFieldException ex) {
						Logger.error(this, "Error while set view model to binding", ex);
					}
				}
			});
		} catch (IllegalAccessException ex) {
			Logger.error(this, "Error while set binding to annotated field", ex);
		} catch (InstantiationException ex) {
			Logger.error(this, "Error while creating view model", ex);
		} catch (NoSuchFieldException ex) {
			Logger.error(this, "Error while set view model to binding", ex);
		}
	}

	private void bindAll(ViewDataBinding binding) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
		createViewModels(binding);
		if (toolbarAnnotation.isPresent()) {
			android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) binding.getRoot().findViewById(toolbarAnnotation.get().value());
			this.setSupportActionBar(toolbar);
			toolbar.setOnMenuItemClickListener(this);
			bindMenuListeners();
			if (getSupportActionBar() == null)
				throw new RuntimeException("support action bar is null");
			getSupportActionBar().setHomeButtonEnabled(toolbarAnnotation.get().homeButton());
			getSupportActionBar().setDisplayHomeAsUpEnabled(toolbarAnnotation.get().homeAsUp());

			if (drawerToggleAnnotation.isPresent()) {
				DrawerLayout viewById = (DrawerLayout) binding.getRoot().findViewById(drawerToggleAnnotation.get().value());
				drawerToggle = Optional.of(new ActionBarDrawerToggle(this, viewById, drawerToggleAnnotation.get().openDrawerDescRes(), drawerToggleAnnotation.get().closeDrawerDescRes()));
				viewById.setDrawerListener(drawerToggle.get());
			}
		}
	}

	private void bindMenuListeners() {
		Class<? extends BindingActivity> aClass = this.getClass();
		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method method : declaredMethods) {
			MenuItemClick annotation = method.getAnnotation(MenuItemClick.class);
			if (annotation != null) {
				method.setAccessible(true);
				menuListeners.put(annotation.value(), method);
			}
		}
	}

	private boolean viewmodelsCreated = false;
	private void createViewModels(ViewDataBinding binding) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
		if(viewmodelsCreated) return;

		Class<? extends BindingActivity> aClass = this.getClass();
		Field[] fields = aClass.getDeclaredFields();
		for (Field field : fields) {
			if(!field.isAccessible()) field.setAccessible(true);

			if (field.getAnnotation(Binding.class) != null) {
				field.set(this, binding);
			}
			BindTo annotation = field.getAnnotation(BindTo.class);
			if (annotation != null) {
				Object value = field.getType().newInstance();
				field.set(this, value);
				Class<?> value1 = annotation.value();
				if (value1 != BindTo.class)
					BRHelper.addBrClass(value1);
				Integer o = BRHelper.getBrByName(field.getName());
				binding.setVariable(o, value);
			}
		}
		viewmodelsCreated = true;
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if(drawerToggle.isPresent())
			drawerToggle.get().syncState();
	}

	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(drawerToggle.isPresent())
			drawerToggle.get().onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		return drawerToggle.map(new Function<ActionBarDrawerToggle, Boolean>() {
			@Override
			public Boolean apply(ActionBarDrawerToggle dt) {
				return dt.onOptionsItemSelected(item);
			}
		}).orElse(false) || onMenuItemClick(item);
	}

	@SuppressWarnings("TryWithIdenticalCatches")
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		try {
			int itemId = item.getItemId();
			if (menuListeners.containsKey(itemId)) {
				menuListeners.get(itemId).invoke(this);
				return true;
			}
		} catch (InvocationTargetException e) {
			Logger.error(this, "Error while invoke menu item click listener", e);
		} catch (IllegalAccessException e) {
			Logger.error(this, "Error while invoke menu item click listener", e);
		}
		return false;
	}
}
