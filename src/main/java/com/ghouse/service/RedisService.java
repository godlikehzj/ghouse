package com.ghouse.service;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by godlikehzj on 2017/1/7.
 */
public class RedisService {
    private Logger log = Logger.getLogger(RedisService.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object object, long activeTime) {
        try {
            redisTemplate.opsForValue().set(key, object, activeTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(String.format("redis set operation for value failed by key [%s]: %s", key, e.getMessage()));
            return;
        }
    }

    public Object get(String key) {
        Object object = null;

        try {
            object = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error(String.format("redis get operation for value failed by key [%s]: %s", key, e.getMessage()));
            return object;
        }

        return object;
    }

    public void removeKey(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(String.format("redis get operation for value failed by key [%s]: %s", key, e.getMessage()));
        }

    }
    public Long increment(String key,long data){
        return redisTemplate.opsForValue().increment(key, data);
    }

    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }
    public Boolean expireAt(String key, final Date date){
        return redisTemplate.expireAt(key, date);
    }
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
