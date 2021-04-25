package io.kang.blog.repository

import io.kang.blog.entity.Blog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogRepository : JpaRepository<Blog, Long>, QuerydslPredicateExecutor<Blog>