package io.kang.consumer.ribbon

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RibbonService {

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun ribbonProvide(): String? {
        return restTemplate.getForObject("http://PROVIDER-SERVER/provide", String::class.java)
    }
}