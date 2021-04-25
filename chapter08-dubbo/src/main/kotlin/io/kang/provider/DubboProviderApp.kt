package io.kang.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class DubboProviderApp

fun main(args: Array<String>) {
    runApplication<DubboProviderApp>(*args)
}