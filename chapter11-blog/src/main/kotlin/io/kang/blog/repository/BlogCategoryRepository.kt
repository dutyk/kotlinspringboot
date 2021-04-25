package io.kang.blog.repository

import io.kang.blog.entity.BlogCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogCategoryRepository : JpaRepository<BlogCategory, Long>, QuerydslPredicateExecutor<BlogCategory>