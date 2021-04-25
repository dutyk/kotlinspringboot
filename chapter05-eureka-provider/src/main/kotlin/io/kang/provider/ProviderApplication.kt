package io.kang.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class ProviderApplication

fun main(args: Array<String>) {
    runApplication<ProviderApplication>(*args)
}