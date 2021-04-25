package io.kang.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "data")
class ConsulConfig {
    var env: String? = null
    var user: User? = null
}

class User{
    var username: String? = null
    var password: String? = null

    override fun toString(): String {
        return "${username},${password}"
    }
}