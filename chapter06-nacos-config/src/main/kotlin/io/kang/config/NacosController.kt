package io.kang.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NacosController {
    @Autowired
    lateinit var nacosConfig: NacosConfig

    @Autowired
    lateinit var nacosConfig1: NacosConfig1

    @GetMapping("config")
    fun getNacosConfig(): String {
        return "${nacosConfig.env}-${nacosConfig.user}"
    }

    @GetMapping("config1")
    fun getNacosConfig1(): String {
        return "${nacosConfig1.env}-${nacosConfig1.username}-${nacosConfig1.password}"
    }
}