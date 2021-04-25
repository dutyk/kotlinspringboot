package io.kang.prometheus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer

@SpringBootApplication
class PrometheusApp {
    @Bean
    fun configurer(@Value("\${spring.application.name}") applicationName: String):
            MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer<MeterRegistry>
        { registry -> registry.config().commonTags("application", applicationName) }
    }
}

fun main(args: Array<String>) {
    runApplication<PrometheusApp>(*args)
}