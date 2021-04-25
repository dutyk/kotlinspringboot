package io.kang.stream

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(MySource::class, MySink::class)
class StreamApplication

fun main(args: Array<String>) {
    runApplication<StreamApplication>(*args)
}