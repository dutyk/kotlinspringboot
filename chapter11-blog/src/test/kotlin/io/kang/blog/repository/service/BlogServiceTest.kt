package io.kang.blog.repository.service

import io.kang.blog.repository.BlogDAO
import io.kang.blog.util.PageQueryUtil
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class BlogServiceTest {
    @Autowired
    lateinit var blogService: BlogDAO

    @Test
    fun findBlogListByType() {
        val blogs = blogService.findBlogListByType(0, 3)
        blogs.forEach { println(it) }
    }

    @Test
    fun getTotalBlogs() {
        val map = mutableMapOf<String, Any>()
        map["keyword"] = "Spring"
        map["blogStatus"] = 1
        map["blogCategoryId"] = 24
        map["page"] = "0"
        map["limit"] = "5"
        val pageQueryUtil = PageQueryUtil(map)

        println(blogService.getTotalBlogs(pageQueryUtil))
    }
}