package io.kang.stream

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface MySource {
    @Output("output1")
    fun output1(): MessageChannel
}