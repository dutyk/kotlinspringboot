package io.kang.gateway

import reactor.core.publisher.Mono

fun main(args: Array<String>) {
    val m = Mono.just("string")
    m.subscribe { m ->
        println(m)
        Mono.empty<Void>()
    }
}