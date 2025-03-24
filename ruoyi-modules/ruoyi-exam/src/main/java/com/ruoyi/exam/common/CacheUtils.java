package com.ruoyi.exam.common;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 */
@Component
public class CacheUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 构造本地缓存，设置缓存容量和过期时间
    private final Cache<String, String> LOCAL_CACHE =
            Caffeine.newBuilder().initialCapacity(1024)
                    .maximumSize(10000L) // 最大缓存条数
                    .expireAfterWrite(5L, TimeUnit.MINUTES) // 缓存 5 分钟移除
                    .build();

    /**
     * 获取缓存
     * 二级缓存（先从本地缓存获取，未命中再从远程缓存【redis】中获取，都未未命中再查询数据库并刷新缓存）
     *
     * @param key 缓存key
     * @return 缓存value
     */
    public String getCacheValue(String key) {
        // 本地缓存获取
        String localCache = LOCAL_CACHE.getIfPresent(key);
        if (StrUtil.isNotBlank(localCache)) {
            return localCache;
        }
        // 远程缓存获取
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String redisCache = opsForValue.get(key);
        if (StrUtil.isNotBlank(redisCache)) {
            // 刷新本地缓存
            LOCAL_CACHE.put(key, redisCache);
            return redisCache;
        }
        // 缓存中没有数据
        return "";
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param data
     * @return
     */
    public void setCacheValue(String key, String data) {
        LOCAL_CACHE.put(key, data);
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        // 设置过期时间 5-10分钟随机时间，防止缓存击穿
        long expireTime = 300 + RandomUtil.randomInt(0, 300);
        opsForValue.set(key, data, expireTime, TimeUnit.SECONDS);
    }

}
