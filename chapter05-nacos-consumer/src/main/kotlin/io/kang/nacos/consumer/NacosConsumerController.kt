package io.kang.nacos.consumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class NacosConsumerController {
    @Autowired
    lateinit var restTemplate: RestTemplate
    @Autowired
    lateinit var feignService: FeignService

    @GetMapping("ribbon/hello/nacos")
    fun ribbonHelloNacos(): String? {
        return restTemplate.getForObject("http://nacos-producer/hello/nacos", String::class.java)
    }

    @GetMapping("feign/hello/nacos")
    fun feignHelloNacos(): String {
        return feignService.helloNacos()
    }
}