package io.kang.consumer

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(value = "consul-producer")
interface FeignService {
    @GetMapping("hello/consul")
    fun helloConsul(): String
}