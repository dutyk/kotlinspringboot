package io.kang.blog.repository.service

import io.kang.blog.entity.BlogCategory
import io.kang.blog.repository.BlogCategoryDAO
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class BlogCategoryServiceTest {
    @Autowired
    lateinit var blogCategoryService: BlogCategoryDAO

    @Test
    fun updateByPrimaryKeySelective() {
        val record = BlogCategory()
        record.categoryId = 20
        record.categoryName = "About"
        record.isDeleted = 0
        blogCategoryService.updateByPrimaryKeySelective(record)
    }

    @Test
    fun findCategoryList() {
    }

    @Test
    fun updateByPrimaryKey() {
        val record = BlogCategory()
        record.categoryName = "About"
        record.categoryId = 21
        record.categoryIcon = ""
        record.categoryIcon = ""
        record.isDeleted = 1
        record.categoryRank = 2
        record.createTime = Date()
        blogCategoryService.updateByPrimaryKey(record)
    }

    @Test
    fun deleteByPrimaryKey() {
        blogCategoryService.deleteByPrimaryKey(21)
    }
}