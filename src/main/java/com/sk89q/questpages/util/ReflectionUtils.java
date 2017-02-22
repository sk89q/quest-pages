package com.sk89q.questpages.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Object callGetter(Object object, String name) {
        try {
            Method method = object.getClass().getMethod(name);
            return method.invoke(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to call getter " + name + " on " + object.getClass().getName(), e);
        }
    }

    public static void callSetter(Object object, String name, Object value) {
        try {
            Method method = null;
            for (Method m : object.getClass().getMethods()) {
                if (m.getName().equals(name) && m.getParameterTypes().length == 1) {
                    method = m;
                }
            }
            if (method == null) {
                throw new RuntimeException("Couldn't find method named " + name + " on " + object.getClass().getName());
            }
            method.invoke(object, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to call setter " + name + " on " + object.getClass().getName(), e);
        }
    }

}
