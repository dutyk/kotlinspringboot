package io.kang.example.repository

import io.kang.example.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface UserDomainRepository: JpaRepository<User, Long>, QuerydslPredicateExecutor<User>