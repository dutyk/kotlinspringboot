package io.kang.example.redis

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class RedisDataTest {
    @Autowired
    lateinit var redisData: RedisData

    @Test
    fun `save redis key value with expire time`() {
        runBlocking {
            redisData.saveStringWithExpire("hello1", "helloWorld1", 2L)
            kotlinx.coroutines.delay(2 * 1000L)
        }
        println("get key")
        val value = redisData.getString("hello1")
        Assert.assertNull(value)
    }

    @Test
    fun `save and get redis value`() {
        val key = "hello"
        redisData.saveString(key, "helloWorld")
        val value = redisData.getString(key)
        Assert.assertEquals(value, "helloWorld")
    }

    @Test
    fun `save redis list with expire time`() {
        runBlocking {
            val values = arrayListOf("hi1", "hi2", "hi3")
            redisData.saveListWithExpire("listKey1", values, 2L)
            kotlinx.coroutines.delay(2 * 1000L)
        }

        val values = redisData.getListValue("listKey1", 0L, 3L)
        Assert.assertEquals(values?.size, 0)
    }

    @Test
    fun `save and get list values`() {
        val key = "listKey"
        redisData.saveList(key, arrayListOf("hi1", "hi2", "hi3"))
        val values = redisData.getListValue("listKey", 0L, 1L)
        Assert.assertEquals(2, values?.size)
    }

    @Test
    fun `save redis set with expire time`() {
        runBlocking {
            val values = arrayOf("hello", "hello", "world")
            redisData.saveSetWithExpire("setKey3", values, 2L)
            kotlinx.coroutines.delay(2 * 1000L)
        }

        val values = redisData.getSetValues("setKey3");
        Assert.assertEquals(values?.size, 0)
    }

    @Test
    fun `save and get redis two set diff`() {
        redisData.saveSet("setKey1", arrayOf("hello", "hello", "world", "wide"))
        redisData.saveSet("setKey2", arrayOf("hello", "hello", "world", "women"))
        val diffSet = redisData.getSetDiff("setKey1", "setKey2")
        Assert.assertEquals(diffSet?.size, 1)
    }

    @Test
    fun `save redis zset with expire time`() {
        runBlocking {
            val values = arrayOf(Pair("xiaoming",98.0), Pair("xiaoli", 90.0), Pair("wangming", 100.0))
            redisData.saveZsetWithExpire("zsetKey2", values, 2L)
            kotlinx.coroutines.delay(2_000)
        }

        val values = redisData.getZsetRangeByScore("zsetKey2", 95.0, 99.0);
        Assert.assertEquals(values?.size, 0)
    }

    @Test
    fun `saven and get redis zset values by score`() {
        redisData.saveZset("zsetKey1", arrayOf(Pair("xiaoming",98.0), Pair("xiaoli", 90.0), Pair("wangming", 100.0)))
        val values = redisData.getZsetRangeByScore("zsetKey1", 95.0, 100.0)
        Assert.assertEquals(2, values?.size)
    }

    @Test
    fun `save redis hashs with expire time`() {
        runBlocking {
            val aMap = mapOf(Pair("key1","value1"), Pair("key2", "value2"))
            redisData.saveHashWithExpire("hashKey2", aMap, 2L)
            kotlinx.coroutines.delay(2_000)
        }

        val values = redisData.getHashValues("hashKey2")
        Assert.assertEquals(0, values?.size)
    }

    @Test
    fun `save and get redis hash values`() {
        val aMap = mapOf(Pair("key1","value1"), Pair("key2", "value2"))
        redisData.saveHash("hashKey1", aMap)
        val values = redisData.getHashValues("hashKey1")
        Assert.assertEquals(2, values?.size)
    }
}