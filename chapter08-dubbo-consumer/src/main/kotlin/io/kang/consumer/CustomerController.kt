package io.kang.consumer

import org.springframework.web.bind.annotation.RestController
import io.kang.provider.service.DubboEchoService
import org.apache.dubbo.config.annotation.Reference
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping

@RestController
class CustomerController {
    @Reference
    lateinit var dubboEchoService: DubboEchoService

    @GetMapping("/dubbo/echo/{name}")
    fun dubboEcho(@PathVariable("name") name: String): String {
        return dubboEchoService.echo(name)
    }
}