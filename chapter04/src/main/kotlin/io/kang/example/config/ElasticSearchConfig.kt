package io.kang.example.config

import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class ElasticSearchConfig {
    @PostConstruct
    fun init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false")
    }
}