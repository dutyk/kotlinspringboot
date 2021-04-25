package io.kang.provider.controller

import io.kang.provider.domain.TbUser
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import io.kang.provider.mapper.TbUserMapper
import org.springframework.beans.factory.annotation.Autowired

@RestController
class ProviderController {
    @Autowired
    lateinit var userMapper: TbUserMapper

    @PostMapping("/add/user")
    fun add(@RequestBody user: TbUser) {
        println("add user: $user")
        user.name = "provider"
        userMapper.insert(user)
    }
}