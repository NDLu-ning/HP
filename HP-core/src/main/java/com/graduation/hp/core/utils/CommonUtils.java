package com.graduation.hp.core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    /**
     * @return numeric string converted or null if it cannot be converted.
     */
    public static Long toLong(String string) {
        try {
            return Long.decode(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @return numeric string converted or null if it cannot be converted.
     */
    public static Double toDouble(String string) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int toInt(boolean value) {
        return (value) ? 1 : 0;
    }

    public static boolean toBoolean(int value) {
        return (value != 0);
    }

    public static <A, B> Pair<List<A>, List<B>> toPair(Map<A, B> map) {
        if (map == null) {
            return null;
        }

        final List<A> firstList = new ArrayList<>();
        final List<B> secondList = new ArrayList<>();
        for (Map.Entry<A, B> entry : map.entrySet()) {
            firstList.add(entry.getKey());
            secondList.add(entry.getValue());
        }

        return new Pair<>(firstList, secondList);
    }

    public static <A, B> Map<A, B> toMap(List<A> firstList, List<B> secondList) {
        if (firstList == null || secondList == null) {
            return null;
        }

        final Map<A, B> hashMap = new HashMap<>(firstList.size());
        for (int i = 0; i < firstList.size(); i++) {
            hashMap.put(firstList.get(i), secondList.get(i));
        }

        return hashMap;
    }

    public static String toBase64String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static byte[] toByteArray(String base64EncodedString) {
        return Base64.decode(base64EncodedString, Base64.NO_WRAP);
    }

    public static long[] toLongArray(List<Long> longList) {
        if (longList == null) {
            return null;
        }

        final long[] array = new long[longList.size()];
        for (int i = 0; i < longList.size(); i++) {
            array[i] = longList.get(i);
        }
        return array;
    }

    public static List<Long> toLongList(long[] array) {
        final List<Long> list = new ArrayList<>(array.length);
        for (long item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * Converts density pixels (dp) to pixels (px)
     *
     * @param dpi A value in dp (density independent pixels) unit.
     * @param context A context
     * @return The pixel value of the density pixels
     */
    public static int dpiToPixels(int dpi, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * Gets the number of pixels represented by the given dimension resource.
     *
     * @param resId the dimension resource ID
     * @return the number of pixels
     */
    public static int getDimensionPixelSize(Context context, int resId) {
        final Resources res = context.getResources();
        final int pixels = res.getDimensionPixelSize(resId);

        return pixels;
    }

    public static boolean objectsEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
