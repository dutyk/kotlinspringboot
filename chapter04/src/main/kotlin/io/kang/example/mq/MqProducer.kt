package io.kang.example.mq

import org.apache.rocketmq.client.producer.SendCallback
import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.spring.core.RocketMQTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class MqProducer {
    @Autowired
    lateinit var rocketMQTemplate: RocketMQTemplate

    fun sendMessage(orderPaidEvent: OrderPaidEvent) {
        println("send message: $orderPaidEvent")
        rocketMQTemplate.send("kotlin-topic", MessageBuilder.withPayload(orderPaidEvent).build())
    }

    fun sendMessageWithTag(orderPaidEvent: OrderPaidEvent) {
        println("send message: $orderPaidEvent, tag: kotlin-tag")
        rocketMQTemplate.convertAndSend("kotlin-topic:kotlin-tag", orderPaidEvent)
    }

    fun convertAndSendMessage(orderPaidEvent: OrderPaidEvent) {
        println("convertAndSend message: $orderPaidEvent")
        rocketMQTemplate.convertAndSend("kotlin-topic", orderPaidEvent)
    }

    fun asyncSendMessage(orderPaidEvent: OrderPaidEvent) {
        println("async send single message: $orderPaidEvent")
        rocketMQTemplate.asyncSend("kotlin-topic", orderPaidEvent, object: SendCallback{
            override fun onSuccess(p0: SendResult?) {
                println("async send success: $p0")
            }

            override fun onException(p0: Throwable?) {
                throw Exception(p0)
            }
        },1000L)
    }

    fun syncSendBatchMessage(orderPaidEvents: List<OrderPaidEvent>) {
        println("sync send single message: $orderPaidEvents")
        val msg = orderPaidEvents.map { o -> MessageBuilder.withPayload(o).build() }
        rocketMQTemplate.syncSend("kotlin-topic", msg, 60000L)
    }

    fun syncSendMessage(orderPaidEvent: OrderPaidEvent) {
        println("sync send single message: $orderPaidEvent")
        rocketMQTemplate.syncSend("kotlin-topic", orderPaidEvent)
    }
}