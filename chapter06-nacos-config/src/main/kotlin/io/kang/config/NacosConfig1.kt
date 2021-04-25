package io.kang.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class NacosConfig1 {
    @Value(value = "\${data1.env:dev}")
    var env: String? = null
    @Value(value = "\${data1.username}")
    var username: String? = null
    @Value(value = "\${data1.password}")
    var password: String? = null
}