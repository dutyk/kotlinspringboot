package io.kang.zuul

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

//动态路由
//过滤
//权限校验
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
class ZuulApplication

fun main(args: Array<String>) {
    runApplication<ZuulApplication>(*args)
}