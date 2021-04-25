package io.kang.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class GateWayApplication

fun main(args: Array<String>) {
    runApplication<GateWayApplication>(*args)
}