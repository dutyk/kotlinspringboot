package io.kang.example.mq

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener
import org.apache.rocketmq.spring.core.RocketMQListener
import org.springframework.stereotype.Component

@Component
@RocketMQMessageListener(topic = "kotlin-topic", consumerGroup = "kotlin-consumer")
class MqConsumer: RocketMQListener<OrderPaidEvent> {

    override fun onMessage(p0: OrderPaidEvent?) {
        println("OrderPaidEventConsumer received: $p0")
    }
}