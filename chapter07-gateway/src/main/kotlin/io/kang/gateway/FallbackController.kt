package io.kang.gateway

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FallbackController {
    @GetMapping("/fallback")
    fun fallback(): String {
        return "I'm Spring Cloud Gateway fallback."
    }
}