package com.graduation.hp.core.utils;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Created by Ning on 2019/2/11.
 */

public class CommonUtils {

    private CommonUtils() {
    }

    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class)) {
            return true;
        }
        return false;
    }

    public static boolean isBaseType(Class<?> clazz) {
        return clazz == String.class || clazz == Byte.TYPE
                || clazz == Long.TYPE || clazz == Double.TYPE
                || clazz == Float.TYPE || clazz == Character.TYPE
                || clazz == Short.TYPE || clazz == Boolean.TYPE || clazz == Integer.TYPE;
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    public static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
