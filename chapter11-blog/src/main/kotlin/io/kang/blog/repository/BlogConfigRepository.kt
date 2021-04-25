package io.kang.blog.repository

import io.kang.blog.entity.BlogConfig
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogConfigRepository : JpaRepository<BlogConfig, String>, QuerydslPredicateExecutor<BlogConfig>