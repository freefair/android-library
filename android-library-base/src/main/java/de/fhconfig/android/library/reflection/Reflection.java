package de.fhconfig.android.library.reflection;

import android.support.annotation.Nullable;


import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.fhconfig.android.library.Logger;

public class Reflection {

	private static Logger log = Logger.forClass(Reflection.class);

	public static <T> List<Field> getAllFields(Class<T> clazz){
		return getAllFields(clazz, Predicates.<Field>alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Predicate<Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>absent(), filter);
	}

	public static <T> List<Field> getDeclaredFields(Class<T> clazz, Class<? super T> upToExcluding){
		return getAllFields(clazz, Optional.<Class<? super T>>fromNullable(upToExcluding), Predicates.alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Class<? super T> upToExcluding){
		return getAllFields(clazz, upToExcluding, Predicates.alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Class<? super T> upToExcluding, Predicate<? super Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>fromNullable(upToExcluding), filter);
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Optional<Class<? super T>> upToExcluding, Predicate<? super Field> filter){

		List<Field> fields = new LinkedList<>();

		Class<?> currentClass = clazz;

		do {
			log.verbose("Now checking class " + clazz.getName());
			for (Field field : currentClass.getDeclaredFields()){
				log.verbose("Checking field " + field.getName());
				if(filter.apply(field)) {
					fields.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		} while (currentClass != null && !(upToExcluding.isPresent() && currentClass.equals(upToExcluding.orNull())));

		return fields;
	}

	@Nullable
	public static Class<?> getClassInClass(Class<?> rootClass, String innerClassName) {
		for (Class<?> clazz : rootClass.getClasses()) {
			if (clazz.getSimpleName().equals(innerClassName)) {
				return clazz;
			}
		}
		return null;
	}

	public static List<Class<?>> getActualTypeArguments(Class<?> clazz, Class<?> iface)
	{
		Type[] genericInterfaces = clazz.getGenericInterfaces();
		for (Type type : genericInterfaces) {
			ParameterizedType t = ((ParameterizedType) type);
			if(t.getRawType() instanceof Class && iface.isAssignableFrom((Class) t.getRawType()))
			{
				List<Class<?>> actualTypeArguments = new ArrayList<>(t.getActualTypeArguments().length);

				for (Type typeArgument : t.getActualTypeArguments()) {
					actualTypeArguments.add((Class<?>) typeArgument);
				}


				return actualTypeArguments;
			}
		}
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> cls:
				interfaces){
			List<Class<?>> actualTypeArguments = getActualTypeArguments(cls, iface);
			if(actualTypeArguments != null)
				return actualTypeArguments;
		}
		return null;
	}
}
