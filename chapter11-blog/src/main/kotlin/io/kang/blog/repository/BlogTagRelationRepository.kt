package io.kang.blog.repository

import io.kang.blog.entity.BlogTagRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BlogTagRelationRepository : JpaRepository<BlogTagRelation, Long>, QuerydslPredicateExecutor<BlogTagRelation>