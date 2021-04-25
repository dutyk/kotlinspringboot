package io.kang.prometheus

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    private var counter: Counter? = null

    constructor(registry: MeterRegistry, @Value("\${spring.application.name}") applicationName: String) {
        this.counter = registry.counter("index.api.counter", "application", applicationName)
    }

    @GetMapping("/prometheus")
    fun testPrometheus(): String {
        this.counter?.increment()
        return "hello prometheus"
    }
}