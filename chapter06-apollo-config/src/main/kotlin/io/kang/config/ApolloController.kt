package io.kang.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApolloController {
    @Autowired
    lateinit var apolloConfig: ApolloConfig

    @GetMapping("config")
    fun getApolloConfig(): String {
        return "${apolloConfig.env},${apolloConfig.user}"
    }
}