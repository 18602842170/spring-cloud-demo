package com.cloud.client.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisService<V, HK> {
    
    // 在构造器中获取redisTemplate实例, key(not hashKey) 默认使用String类型
    private RedisTemplate<String, V> redisTemplate;
    // 在构造器中通过redisTemplate的工厂方法实例化操作对象
    private HashOperations<String, HK, V> hashOperations;
    private ListOperations<String, V> listOperations;
    private ZSetOperations<String, V> zSetOperations;
    private SetOperations<String, V> setOperations;
    private ValueOperations<String, V> valueOperations;
    
    // IDEA虽然报错,但是依然可以注入成功, 实例化操作对象后就可以直接调用方法操作Redis数据库
    @Autowired
    public RedisService(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }
    
    public void hashPut(String key, HK hashKey, V value) {
        hashOperations.put(key, hashKey, value);
    }
    
    public Map<HK, V> hashFindAll(String key) {
        return hashOperations.entries(key);
    }
    
    public V hashGet(String key, HK hashKey) {
        return hashOperations.get(key, hashKey);
    }
    
    public void hashRemove(String key, HK hashKey) {
        hashOperations.delete(key, hashKey);
    }
    
    public Long listPush(String key, V value) {
        return listOperations.rightPush(key, value);
    }
    
    public Long listUnshift(String key, V value) {
        return listOperations.leftPush(key, value);
    }
    
    public List<V> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }
    
    public V listLPop(String key) {
        return listOperations.leftPop(key);
    }
    
    public void setValue(String key, V value) {
        valueOperations.set(key, value);
    }
    
    public void setValue(String key, V value, long timeout) {
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }
    
    public V getValue(String key) {
        return valueOperations.get(key);
    }
    
    public void remove(String key) {
        redisTemplate.delete(key);
    }
    
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }
}
