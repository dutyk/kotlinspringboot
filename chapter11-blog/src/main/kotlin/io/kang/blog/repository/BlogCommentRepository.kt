package io.kang.blog.repository

import io.kang.blog.entity.BlogComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogCommentRepository : JpaRepository<BlogComment, Long>, QuerydslPredicateExecutor<BlogComment>