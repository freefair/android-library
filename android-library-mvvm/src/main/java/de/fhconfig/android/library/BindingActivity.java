package de.fhconfig.android.library;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhconfig.android.library.annotations.BindTo;
import de.fhconfig.android.library.annotations.Binding;
import de.fhconfig.android.library.annotations.DrawerToggle;
import de.fhconfig.android.library.annotations.Event;
import de.fhconfig.android.library.annotations.Layout;
import de.fhconfig.android.library.annotations.MenuItemClick;
import de.fhconfig.android.library.annotations.Toolbar;
import de.fhconfig.android.library.annotations.specific.EventName;
import de.fhconfig.android.library.annotations.specific.EventNames;
import de.fhconfig.android.library.injection.annotation.InjectAnnotation;
import de.fhconfig.android.library.ui.GeneralEventListener;
import de.fhconfig.android.library.ui.injection.InjectionAppCompatActivity;
import java8.util.Optional;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

public class BindingActivity extends InjectionAppCompatActivity implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {


	private Map<Integer, Method> menuListeners = new HashMap<>();

	@InjectAnnotation
	private Optional<Layout> layoutAnnotation;
	@InjectAnnotation
	private Optional<Toolbar> toolbarAnnotation;
	@InjectAnnotation
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
				if(event == null || EventNames.NONE.getName().equals(event)){
					event = method.getName();
				}
				View view = findViewById(value);
				bindEventToView(method, event, view);
				continue;
			}
			List<AnnotationHolder> collect = StreamSupport.of(method.getAnnotations()).map(a -> new AnnotationHolder(a, a.annotationType().getAnnotation(EventName.class)))
					.filter(a -> a.name != null).collect(Collectors.toList());
			for (AnnotationHolder a : collect) {
				try {
					Method value = a.a.annotationType().getMethod("value");
					int invoke = (int)value.invoke(a.a);
					String event = a.name.value().getName();
					bindEventToView(method, event, findViewById(invoke));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	private void bindEventToView(Method method, String event, View view) {
		GeneralEventListener byView = GeneralEventListener.getByView(view);
		method.setAccessible(true);
		byView.bindEvent(event, (sender, args) -> method.invoke(this, args));
	}

	private void inflateAndBind(){
		try {
			ViewDataBinding binding = DataBindingUtil.setContentView(this, layoutAnnotation.map(Layout::value).orElse(-1));
			bindAll(binding);
		} catch (IllegalAccessException ex){
			Logger.error(this, "Error while set binding to annotated field", ex);
		} catch (InstantiationException ex){
			Logger.error(this, "Error while creating view model", ex);
		} catch (NoSuchFieldException ex){
			Logger.error(this, "Error while set view model to binding", ex);
		}
	}

	private void bindAll(ViewDataBinding binding) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
		createViewModels(binding);
		if(toolbarAnnotation.isPresent()){
			android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)binding.getRoot().findViewById(toolbarAnnotation.get().value());
			this.setSupportActionBar(toolbar);
			toolbar.setOnMenuItemClickListener(this);
			bindMenuListeners();
			if(getSupportActionBar() == null) throw new RuntimeException("support action bar is null");
			getSupportActionBar().setHomeButtonEnabled(toolbarAnnotation.get().homeButton());
			getSupportActionBar().setDisplayHomeAsUpEnabled(toolbarAnnotation.get().homeAsUp());

			if(drawerToggleAnnotation.isPresent()){
				DrawerLayout viewById = (DrawerLayout)binding.getRoot().findViewById(drawerToggleAnnotation.get().value());
				drawerToggle = Optional.of(new ActionBarDrawerToggle(this, viewById, drawerToggleAnnotation.get().openDrawerDescRes() , drawerToggleAnnotation.get().closeDrawerDescRes()));
				viewById.setDrawerListener(drawerToggle.get());
			}
		}
	}

	private void bindMenuListeners(){
		Class<? extends BindingActivity> aClass = this.getClass();
		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method method : declaredMethods){
			MenuItemClick annotation = method.getAnnotation(MenuItemClick.class);
			if(annotation != null){
				method.setAccessible(true);
				menuListeners.put(annotation.value(), method);
			}
		}
	}

	private void createViewModels(ViewDataBinding binding) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
		Class<? extends BindingActivity> aClass = this.getClass();
		Field[] fields = aClass.getDeclaredFields();
		for(Field field : fields){
			if(field.getAnnotation(Binding.class) != null){
				field.setAccessible(true);
				field.set(this, binding);
			}
			BindTo annotation = field.getAnnotation(BindTo.class);
			if(annotation != null){
				field.setAccessible(true);
				Object value = field.getType().newInstance();
				field.set(this, value);
				Class<?> value1 = annotation.value();
				if(value1 != BindTo.class)
					BRHelper.addBrClass(value1);
				Integer o = BRHelper.getBrByName(field.getName());
				binding.setVariable(o, value);
			}
		}
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.ifPresent(ActionBarDrawerToggle::syncState);
	}

	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.ifPresent(dt -> dt.onConfigurationChanged(newConfig));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return drawerToggle.map(dt -> dt.onOptionsItemSelected(item)).orElse(false) || onMenuItemClick(item);
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
