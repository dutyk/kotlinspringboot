package io.kang.consumer

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping

@RestController
class ConsumerController {
    @Autowired
    lateinit var restTemplate: RestTemplate
    @Autowired
    lateinit var feignService: FeignService

    @GetMapping("ribbon/hello/consul")
    fun ribbonHelloConsul(): String? {
        return restTemplate.getForObject("http://consul-producer/hello/consul", String::class.java)
    }

    @GetMapping("feign/hello/consul")
    fun feignHelloConsul(): String {
        return feignService.helloConsul()
    }
}