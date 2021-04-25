package io.kang.consumer.ribbon

import com.netflix.loadbalancer.IRule
import com.netflix.loadbalancer.RandomRule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableEurekaClient
class RibbonApplication {
    @Bean
    @LoadBalanced
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun ribbonRule(): IRule {
        return RandomRule()
    }
}

fun main(args: Array<String>) {
    runApplication<RibbonApplication>(*args)
}