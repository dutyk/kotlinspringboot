package io.kang.zuul

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class ZuulProperty {
    @Bean
    @ConfigurationProperties("zuul")
    @RefreshScope
    @Primary
    fun zuulProperties(): ZuulProperties {
        return ZuulProperties()
    }
}