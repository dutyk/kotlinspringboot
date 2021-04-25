package io.kang.sentinel

import com.alibaba.csp.sentinel.annotation.SentinelResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.alibaba.csp.sentinel.slots.block.BlockException

@RestController
class SentinelController {
    @GetMapping("/hello")
    @SentinelResource(value = "helloApi", blockHandler = "handleException", blockHandlerClass = [ExceptionUtil::class])
    fun hello(): String {
        return "hello sentinel"
    }

    @GetMapping("/hello1")
    @SentinelResource(value = "helloApi1", blockHandler = "exceptionHandler")
    fun hello1(): String {
        return "hello1 sentinel"
    }

    @GetMapping("/hello2")
    @SentinelResource(value = "helloNacosApi", blockHandler = "exceptionHandler")
    fun helloNacos(): String {
        return "hello1 sentinel in nacos"
    }

    fun exceptionHandler(s: Long, ex: BlockException): String {
        println(ex.printStackTrace())
        return "Oops, error occurred at $s"
    }
}