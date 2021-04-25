package io.kang.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class DubboConsumerApp

fun main(args: Array<String>) {
    runApplication<DubboConsumerApp>(*args)
}