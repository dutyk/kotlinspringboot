package io.kang.blog.repository.service

import io.kang.blog.repository.BlogCommentDAO
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
class BlogCommentServiceTest {
    @Autowired
    lateinit var blogCommentService: BlogCommentDAO

    @Test
    fun findBlogCommentList() {
        val param = mutableMapOf<String, Any>()
        param["start"] = 0
        param["limit"] = 5
        param["blogId"] = 4
        param["commentStatus"] = 1
        val comments = blogCommentService.findBlogCommentList(param)

        comments.forEach { println(it) }
    }
}