package com.ghouse.utils;

import com.ghouse.service.RedisService;

/**
 * Created by godlikehzj on 2017/1/7.
 */
public class RedisUtil {
    private static RedisService redisService = (RedisService) SpringContext.getBean("redisService");

    public static final long activeTime = 9999999999L;
    public static final long valueTime = 60*5L;
    public static final long smsTime = 60*10L;
    //相关key
    public static final String smskey = "sms/"; //后接用户手机号组成key

    public static void set(String key, Object value) {
        redisService.set(key, value, RedisUtil.valueTime);
    }

    public static void set(String key, Object value,Long time) {
        redisService.set(key, value,time);
    }

    public static Object get(String key) {
        return redisService.get(key);
    }

    public static void removeKey(String key) {
        redisService.removeKey(key);
    }
    public static Long increment(String key) {
        return redisService.increment(key, 1);
    }

    public static Long increment(String key, long data) {
        return redisService.increment(key, data);
    }
}
