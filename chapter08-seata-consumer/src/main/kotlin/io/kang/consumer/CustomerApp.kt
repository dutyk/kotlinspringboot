package io.kang.consumer

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableDiscoveryClient
@EnableFeignClients // 扫描 @FeignClient 注解
@MapperScan("io.kang.consumer.mapper")
class CustomerApp

fun main(args: Array<String>) {
    runApplication<CustomerApp>(*args)
}