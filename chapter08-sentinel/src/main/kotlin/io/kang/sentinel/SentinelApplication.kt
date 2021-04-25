package io.kang.sentinel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SentinelApplication {
    @Bean
    fun sentinelResourceAspect(): SentinelResourceAspect {
        return SentinelResourceAspect()
    }
}

fun main(args: Array<String>) {
    runApplication<SentinelApplication>(*args)
}