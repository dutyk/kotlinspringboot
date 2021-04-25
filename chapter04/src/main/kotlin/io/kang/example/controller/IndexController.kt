package io.kang.example.controller

import io.kang.example.entity.User
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PreFilter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {
    @GetMapping("/")
    fun index(): String {
        return "Hello, Kotlin for Springboot!!"
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello/pre/user")
    fun rolePreUserHello(): String {
        println("pre filter admin user")
        return "Hello, I have admin role"
    }

    @PostAuthorize("hasRole('USER')")
    @GetMapping("/hello/post/user")
    fun rolePostUserHello(): String {
        println("post filter user role")
        return "Hello, I have user role"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello/admin")
    fun roleAdminHello(): String {
        println("pre filter admin user")
        return "Hello, I have admin role"
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/hello/any")
    fun anyRoleUserHello(): String {
        return "Hello, I have one of [user, admin] role"
    }

    @PreFilter(value="hasRole('USER') and filterObject.age > 50")
    @PostMapping("/user/prefilter")
    fun preFilterUser(@RequestBody user: List<User>): List<User> {
        println("pre filter user")
        return user
    }

    @PostFilter(value="hasRole('USER') and filterObject.age > 50")
    @PostMapping("/user/postfilter")
    fun postFilterUser(@RequestBody user: List<User>): List<User> {
        println("post filter user")
        return user
    }

    @PreFilter(value="hasRole('ADMIN') and filterObject.age > 50")
    @PostMapping("/user/admin/prefilter")
    fun preFilterAdmin(@RequestBody user: List<User>): List<User> {
        println("pre filter user")
        return user
    }

    @PreFilter(value="hasRole('ADMIN') and filterObject.userName.equals('test02')")
    @PostMapping("/user/prefilter1")
    fun preFilterUser1(@RequestBody user: List<User>): List<User> {
        println("pre filter user")
        return user
    }
}