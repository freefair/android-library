package io.freefair.android.mvvm;

import android.support.annotation.AnyRes;

import java.util.HashSet;
import java.util.Set;

import io.freefair.android.util.logging.AndroidLogger;
import io.freefair.android.util.logging.Logger;

public class BrHelper {
    private static Set<Class<?>> brClasses = new HashSet<>();
    private static Logger log = AndroidLogger.forClass(BrHelper.class);

    @AnyRes
    public static int getBrByName(String name) {
        for (Class<?> brClass : brClasses) {
            try {
                return (Integer) brClass.getDeclaredField(name).get(null);
            } catch (Exception ex) {
                log.error("Error while getDeclaredField: ", ex);
            }
        }
        return -1;
    }

    public static void addBrClass(Class<?> brClass) {
        brClasses.add(brClass);
    }
}
