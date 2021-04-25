package io.kang.zk.consumer

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(value = "zk-produce", path = "/")
@Component
interface FeignService {
    @GetMapping("hello/zk")
    fun helloZk(): String
}