package io.kang.example.mq

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MqController {
    @Autowired
    lateinit var mqProducer: MqProducer

    @PostMapping("/mq/send")
    fun sendMsg(@RequestBody orderPaidEvent: OrderPaidEvent) {
        mqProducer.sendMessage(orderPaidEvent)
    }

    @PostMapping("/mq/send/tag")
    fun sendMsgTag(@RequestBody orderPaidEvent: OrderPaidEvent) {
        mqProducer.sendMessageWithTag(orderPaidEvent)
    }

    @PostMapping("/mq/convertAndSend")
    fun convertAndSendMsg(@RequestBody orderPaidEvent: OrderPaidEvent) {
        mqProducer.convertAndSendMessage(orderPaidEvent)
    }

    @PostMapping("/mq/asyncSend")
    fun asyncAndSendMsg(@RequestBody orderPaidEvent: OrderPaidEvent) {
        mqProducer.asyncSendMessage(orderPaidEvent)
    }

    @PostMapping("/mq/asyncBatchSend")
    fun asyncAndBatchSendMsg(@RequestBody orderPaidEvents: List<OrderPaidEvent>) {
        mqProducer.syncSendBatchMessage(orderPaidEvents)
    }

    @PostMapping("/mq/syncSend")
    fun syncSendMsg(@RequestBody orderPaidEvent: OrderPaidEvent) {
        mqProducer.syncSendMessage(orderPaidEvent)
    }
}