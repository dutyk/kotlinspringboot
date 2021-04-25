package io.kang.gateway

import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/route")
class RouteController {
    @Autowired
    lateinit var redisRouteRepository: RedisRouteRepository

    @PostMapping("/add")
    fun add(@RequestBody routeDefinition: RouteDefinition): Mono<String> {
        redisRouteRepository.save(Mono.just(routeDefinition))
        return Mono.just("add ok")
    }

    @GetMapping("/all")
    fun getAll(): Flux<RouteDefinition> {
        return redisRouteRepository.routeDefinitions
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<String> {
        redisRouteRepository.delete(Mono.just(id))
        return Mono.just("delete ok")
    }
}