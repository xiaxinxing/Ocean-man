package com.ocean.core.commons.handler;


import com.ocean.core.commons.constant.CommonConstant;

import java.util.HashMap;
import java.util.Map;


public class BaseContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();


    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }


    public static void setCurrentUser(String userId) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(CommonConstant.currentUserKey, userId);
    }

    public static String getCurrentUserId() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(CommonConstant.currentUserKey).toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
