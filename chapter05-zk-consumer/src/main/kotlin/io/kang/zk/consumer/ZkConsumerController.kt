package io.kang.zk.consumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@RestController
class ZkConsumerController {
    @Autowired
    lateinit var feignService: FeignService

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping("feign/hello/zk")
    fun feignHelloZk(): String {
        return feignService.helloZk()
    }

    @GetMapping("ribbon/hello/zk")
    fun ribbonHelloZk(): String? {
        return restTemplate.getForObject("http://zk-produce/hello/zk", String::class.java)
    }
}