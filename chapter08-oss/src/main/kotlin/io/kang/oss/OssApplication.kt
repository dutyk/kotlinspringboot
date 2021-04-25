package io.kang.oss

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OssApplication

fun main(args: Array<String>) {
    runApplication<OssApplication>(*args)
}