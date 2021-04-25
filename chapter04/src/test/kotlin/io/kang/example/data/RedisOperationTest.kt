package io.kang.example.data

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit4.SpringRunner
import java.time.Duration

@SpringBootTest
@RunWith(SpringRunner::class)
class RedisOperationTest {
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Test
    fun `test redis add`() {
        redisTemplate.opsForValue().set("key1", "kk", Duration.ofSeconds(10))
        Assert.assertEquals("kk", redisTemplate.opsForValue().get("key1"))
    }

}