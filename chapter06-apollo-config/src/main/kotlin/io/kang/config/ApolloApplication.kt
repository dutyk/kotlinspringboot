package io.kang.config

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableApolloConfig
class ApolloApplication

fun main(args: Array<String>) {
    System.setProperty("env", "dev")
    runApplication<ApolloApplication>(*args)
}