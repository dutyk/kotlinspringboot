package io.kang.gateway

import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.cloud.gateway.support.NotFoundException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.LinkedHashMap
import java.util.LinkedList

@Component
class RedisRouteRepository: RouteDefinitionRepository {
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    val routeKey = "route"

    override fun save(route: Mono<RouteDefinition>): Mono<Void> {
        route
                .subscribe { routeDefinition ->
                    println("${JSON.toJSONString(routeDefinition)}")
                    redisTemplate.opsForHash<String, String>().put(routeKey, routeDefinition.id,
                            JSON.toJSONString(routeDefinition))
                }
        return  Mono.empty<Void>()
    }

    override fun getRouteDefinitions(): Flux<RouteDefinition> {
        if (redisTemplate.hasKey(routeKey)) {
            // redis中拉取路由
            val routeDefinitions = LinkedList<RouteDefinition>()
            redisTemplate
                    .opsForHash<String, String>().values(routeKey)
                    .stream()
                    .forEach { routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition, RouteDefinition::class.java) as RouteDefinition) }
            return Flux.fromIterable(routeDefinitions)
        } else {
            var routes = LinkedHashMap<String, String>()
            redisTemplate.opsForHash<String, String>().putAll(routeKey, routes)
            return Flux.fromIterable(LinkedList<RouteDefinition>())
        }
    }

    override fun delete(routeId: Mono<String>): Mono<Void> {
        routeId
                .subscribe { routeId ->
                    if (redisTemplate.opsForHash<String, String>().hasKey(routeKey, routeId)) {
                        redisTemplate.opsForHash<String, String>().delete(routeKey, routeId)
                    }
                }
        return Mono.empty<Void>()
    }

}