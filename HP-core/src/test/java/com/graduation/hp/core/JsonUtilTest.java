package com.graduation.hp.core;

import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.LogUtils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilTest {

    @Test
    public void testMap2Json() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "3");
        map.put("password", "3");
        System.out.println(JsonUtils.objectToJson(map));
    }

}
