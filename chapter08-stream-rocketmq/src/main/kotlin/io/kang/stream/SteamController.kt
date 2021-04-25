package io.kang.stream

import org.apache.rocketmq.common.message.MessageConst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.messaging.MessageHeaders
import java.util.HashMap

@RestController
class SteamController {
    @Autowired
    lateinit var mySource: MySource

    @GetMapping("/send0/{id}")
    fun send(@PathVariable id: String) {
        val headers = HashMap<String, Any>()
        headers[MessageConst.PROPERTY_TAGS] = "tagStr0"
        headers["partitionKey"] = 0
        mySource.output1().send(MessageBuilder.createMessage("hello world: $id", MessageHeaders(headers)))
    }

    @GetMapping("/send1/{id}")
    fun send1(@PathVariable id: String) {
        val headers = HashMap<String, Any>()
        headers[MessageConst.PROPERTY_TAGS] = "tagStr1"
        headers["partitionKey"] = 1
        mySource.output1().send(MessageBuilder.createMessage("hello world: $id", MessageHeaders(headers)))
    }

    @GetMapping("/send2/{id}")
    fun send2(@PathVariable id: String) {
        val headers = HashMap<String, Any>()
        headers[MessageConst.PROPERTY_TAGS] = "tagStr2"
        headers["partitionKey"] = 1
        mySource.output1().send(MessageBuilder.createMessage("hello world: $id", MessageHeaders(headers)))
    }
}