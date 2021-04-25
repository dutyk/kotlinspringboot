package io.kang.example.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
class RedisData {
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    fun saveString(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun saveStringWithExpire(key: String, value: String, expireSecond: Long) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(expireSecond))
    }

    fun getString(key: String): String? {
        return redisTemplate.opsForValue().get(key);
    }

    fun saveList(key: String, values: List<String>) {
        values.forEach { v ->
            redisTemplate.opsForList().leftPush(key, v)
        }
    }

    fun saveListWithExpire(key: String, values: List<String>, expireSecond: Long) {
        values.forEach { v ->
            redisTemplate.opsForList().leftPush(key, v)
        }
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS)
    }

    fun getListValue(key: String, start: Long, end: Long): List<String>? {
        return redisTemplate.opsForList().range(key, start, end);
    }

    fun saveSet(key: String, values: Array<String>) {
        redisTemplate.opsForSet().add(key, *values)
    }

    fun saveSetWithExpire(key: String, values: Array<String>, expireSecond: Long) {
        redisTemplate.opsForSet().add(key, *values)
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS)
    }

    fun getSetValues(key: String): Set<String>? {
        return redisTemplate.opsForSet().members(key)
    }

    fun getSetDiff(key1: String, key2: String): Set<String>? {
        return redisTemplate.opsForSet().difference(key1, key2)
    }

    fun saveZset(key: String, values: Array<Pair<String, Double>>) {
        values.forEach { v ->  redisTemplate.opsForZSet().add(key, v.first, v.second)}
    }

    fun saveZsetWithExpire(key: String, values: Array<Pair<String, Double>>, expireSecond: Long) {
        values.forEach { v ->  redisTemplate.opsForZSet().add(key, v.first, v.second)}
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS)
    }

    fun getZsetRangeByScore(key: String, minScore: Double, maxScore: Double): Set<String>? {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore)
    }

    fun saveHash(key: String, values: Map<String, String>) {
        redisTemplate.opsForHash<String, String>().putAll(key, values);
    }

    fun saveHashWithExpire(key: String, values: Map<String, String>, expireSecond: Long) {
        redisTemplate.opsForHash<String, String>().putAll(key, values);
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS)
    }

    fun getHashValues(key: String): List<String>? {
        return redisTemplate.opsForHash<String, String>().values(key);
    }
}