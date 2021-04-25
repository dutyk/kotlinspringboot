package io.kang.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsulController {
    @Autowired
    lateinit var consulConfig: ConsulConfig

    @GetMapping("config")
    fun getConsulConfig(): String {
        return "${consulConfig.env}, ${consulConfig.user}"
    }
}