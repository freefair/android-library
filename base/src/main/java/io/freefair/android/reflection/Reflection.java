package io.freefair.android.reflection;

import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.freefair.android.Logger;
import io.freefair.android.util.Optional;
import io.freefair.android.util.Predicate;
import io.freefair.android.util.Predicates;

public class Reflection {

	private static Logger log = Logger.forClass(Reflection.class);

	public static <T> List<Field> getAllFields(Class<T> clazz){
		return getAllFields(clazz, Predicates.<Field>alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Predicate<Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>empty(), filter);
	}

	public static <T> List<Field> getDeclaredFields(Class<T> clazz, Class<? super T> upToExcluding){
		return getAllFields(clazz, Optional.<Class<? super T>>ofNullable(upToExcluding), Predicates.alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Class<? super T> upToExcluding){
		return getAllFields(clazz, upToExcluding, Predicates.alwaysTrue());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Class<? super T> upToExcluding, Predicate<? super Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>ofNullable(upToExcluding), filter);
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Optional<Class<? super T>> upToExcluding, Predicate<? super Field> filter){

		List<Field> fields = new ArrayList<>();

		Class<?> currentClass = clazz;

		do {
			log.verbose("Now checking class " + clazz.getName());
			for (Field field : currentClass.getDeclaredFields()){
				log.verbose("Checking field " + field.getName());
				if(filter.test(field)) {
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
