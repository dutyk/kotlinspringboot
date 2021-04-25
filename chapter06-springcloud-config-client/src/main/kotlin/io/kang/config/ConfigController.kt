package io.kang.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController {
    @Autowired
    lateinit var gitConfig: GitConfig

    @GetMapping("/config")
    fun getConfig(): String {
        return gitConfig.toString()
    }
}