package io.kang.blog.repository.service

import io.kang.blog.repository.BlogTagDAO
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class BlogTagServiceTest {
    @Autowired
    lateinit var blogTagService: BlogTagDAO

    @Test
    fun getTagCount() {
        val tagCountList = blogTagService.getTagCount()
        tagCountList.forEach { println(it) }
    }
}