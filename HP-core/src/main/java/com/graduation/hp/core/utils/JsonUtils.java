package com.graduation.hp.core.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Ning on 2019/2/8.
 */

public class JsonUtils {

    private JsonUtils() {
    }

    public static String objectToJson(Object data) {
        String string = JSON.toJSONString(data);
        return string;
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = JSON.parseObject(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            List<T> list = JSON.parseArray(jsonData, beanType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static RequestBody mapToRequestBody(Map<String, Object> map) {
        LogUtils.d(objectToJson(map));
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objectToJson(map));
    }

    /**
     * map to json, checking symbol \"
     *
     * @param map
     * @return
     */
    public static RequestBody mapToRequestBody2(Map<String, String> map) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objectToJson(map));
    }

    public static RequestBody mapToRequestBody3(String[] keys, Object[] values) {
        Map<String, Object> map = new HashMap<>();
        if (keys == null || keys.length != values.length || values.length == 0) {
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], values[i]);
            }
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objectToJson(map));
    }
}
