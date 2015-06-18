package de.fhconfig.android.library;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.fhconfig.android.library.annotations.BindTo;
import de.fhconfig.android.library.annotations.Binding;
import de.fhconfig.android.library.annotations.Event;
import de.fhconfig.android.library.annotations.Layout;
import de.fhconfig.android.library.annotations.Menu;
import de.fhconfig.android.library.annotations.MenuItemClick;
import de.fhconfig.android.library.annotations.Toolbar;
import de.fhconfig.android.library.reflection.Reflection;
import de.fhconfig.android.library.ui.GeneralEventListener;
import de.fhconfig.android.library.ui.injection.InjectionAppCompatActivity;

public class BindingActivity extends InjectionAppCompatActivity implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {
	private int layoutId;
	private int menuId = -1;
	private int toolbarId = -1;
	private Map<Integer, Method> menuListeners = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resolveAnnotations();
		inflateAndBind();
		createEventListener();
	}

	private void createEventListener() {
		Method[] declaredMethods = this.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			Event annotation = method.getAnnotation(Event.class);
			if (annotation != null) {
				int value = annotation.value();
				String event = annotation.event();
				if(event == null || "".equals(event)){
					event = method.getName();
				}
				View view = findViewById(value);
				GeneralEventListener byView = GeneralEventListener.getByView(view);
				method.setAccessible(true);
				byView.bindEvent(event, (sender, args) -> method.invoke(this, args));
			}
		}
	}

	private void inflateAndBind(){
		try {
			ViewDataBinding binding = DataBindingUtil.setContentView(this, layoutId);
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
		if(toolbarId > -1){
			android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)binding.getRoot().findViewById(toolbarId);
			this.setSupportActionBar(toolbar);
			toolbar.setOnMenuItemClickListener(this);
			bindMenuListeners();
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

	private void resolveAnnotations() {
		Class<? extends BindingActivity> aClass = this.getClass();
		Annotation[] annotations = aClass.getAnnotations();
		for (Annotation annotation : annotations){
			if(annotation instanceof Layout)
				layoutId = ((Layout)annotation).value();
			if(annotation instanceof Menu)
				menuId = ((Menu)annotation).value();
			if(annotation instanceof Toolbar)
				toolbarId = ((Toolbar)annotation).value();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(menuId, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return onMenuItemClick(item);
	}

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
