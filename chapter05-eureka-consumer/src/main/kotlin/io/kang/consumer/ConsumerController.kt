package io.kang.consumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsumerController {
    @Autowired
    lateinit var providerService: ProviderService

    @GetMapping("/feignProvide")
    fun openProvide(): String {
        return providerService.provide()
    }
}