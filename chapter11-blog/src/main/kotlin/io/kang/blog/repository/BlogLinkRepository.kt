package io.kang.blog.repository

import io.kang.blog.entity.BlogLink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogLinkRepository : JpaRepository<BlogLink, Long>, QuerydslPredicateExecutor<BlogLink>