package io.kang.example.mq

import java.math.BigDecimal

class OrderPaidEvent(
        val orderId: String,
        val paidMoney: BigDecimal
){
    constructor():this("0", BigDecimal(0.0))

    override fun toString(): String {
        return "OrderPaidEvent($orderId, $paidMoney)"
    }
}