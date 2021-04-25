package io.kang.gateway

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import reactor.core.publisher.Mono

@Configuration
class RequestRateLimiterConfig {
    @Bean("ipAddressKeyResolver")
    fun ipAddressKeyResolver(): KeyResolver {
        return KeyResolver {
            exchange ->  Mono.just(exchange.request.remoteAddress?.hostName.orEmpty())
        }
    }

    @Bean("apiKeyResolver")
    @Primary
    fun apiKeyResolver(): KeyResolver {
        return KeyResolver {
            exchange ->  Mono.just(exchange.request.path.value())
        }
    }

    @Bean("userKeyResolver")
    fun userKeyResolver(): KeyResolver {
        return KeyResolver {
            exchange ->  Mono.just(exchange.request.queryParams.getFirst("userId").orEmpty())
        }
    }
}