package io.kang.consumer.controller

import io.kang.consumer.domain.TbUser
import io.kang.consumer.feign.ProviderFeignService
import io.kang.consumer.mapper.TbUserMapper
import io.seata.core.context.RootContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import io.seata.spring.annotation.GlobalTransactional
import org.springframework.web.bind.annotation.PostMapping

@RestController
class UserController {
    @Autowired
    lateinit var userMapper: TbUserMapper

    @Autowired
    lateinit var providerFeignService: ProviderFeignService

    @PostMapping("/seata/user/add")
    @GlobalTransactional(rollbackFor = [Exception::class]) // 开启全局事务
    fun add(@RequestBody user: TbUser) {
        println("globalTransactional begin, Xid: ${RootContext.getXID()}")
        // local save
        localSave(user)

        // call provider save
        providerFeignService.add(user)

        // test seata globalTransactional
        throw RuntimeException()
    }

    private fun localSave(user: TbUser) {
        user.name = "customer"
        userMapper.insert(user)
    }
}