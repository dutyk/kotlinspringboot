package io.kang.blog.repository

import io.kang.blog.entity.AdminUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface AdminUserRepository : JpaRepository<AdminUser, Long>, QuerydslPredicateExecutor<AdminUser>