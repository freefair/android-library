package de.fhconfig.android.library.reflection;

import com.google.common.base.Optional;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import de.fhconfig.android.library.Logger;
import de.fhconfig.android.library.predicate.Predicate;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class Reflection {

	private static Logger log = Logger.forClass(Reflection.class);

	public static <T> List<Field> getAllFields(Class<T> clazz){
		return getAllFields(clazz, Optional.<Class<? super T>>absent(), Predicate.always());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Predicate<Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>absent(), filter);
	}

	public static <T> List<Field> getDeclaredFields(Class<T> clazz, Class<? super T> upToExcluding){
		return getAllFields(clazz, Optional.<Class<? super T>>fromNullable(upToExcluding), Predicate.always());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Optional<Class<? super T>> upToExcluding){
		return getAllFields(clazz, upToExcluding, Predicate.always());
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Class<? super T> upToExcluding, Predicate<? super Field> filter){
		return getAllFields(clazz, Optional.<Class<? super T>>fromNullable(upToExcluding), filter);
	}

	public static <T> List<Field> getAllFields(Class<T> clazz, Optional<Class<? super T>> upToExcluding, Predicate<? super Field> filter){

		List<Field> fields = new LinkedList<>();

		Class<?> currentClass = clazz;

		do {
			log.debug("Now checking class " + clazz.getName());
			for (Field field : currentClass.getDeclaredFields()){
				log.debug("Checking field " + field.getName());
				if(filter.apply(field)) {
					fields.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		} while (currentClass != null && !(upToExcluding.isPresent() && currentClass.equals(upToExcluding.orNull())));

		return fields;
	}

	public static Class<?> getClassInClass(Class<?> rootClass, String innerClassName) {
		for (Class<?> clazz : rootClass.getClasses()) {
			if (clazz.getSimpleName().equals(innerClassName)) {
				return clazz;
			}
		}
		return null;
	}
}
