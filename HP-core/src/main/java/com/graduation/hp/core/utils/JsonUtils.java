package com.graduation.hp.core.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

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
}
