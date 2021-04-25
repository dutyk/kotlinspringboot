package io.kang.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

@Component
@RefreshScope
data class GitConfig(
        @Value("\${data.env}")
        val env: String,
        @Value("\${data.user.username}")
        val username: String,
        @Value("\${data.user.password}")
        val password: String
)