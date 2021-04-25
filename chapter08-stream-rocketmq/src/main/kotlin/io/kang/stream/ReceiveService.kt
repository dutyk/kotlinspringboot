package io.kang.stream

import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

@Service
class ReceiveService {
    @StreamListener("input1")
    fun receiveInput1(receiveMsg: String) {
        println("input1 receive: $receiveMsg")
    }

    @StreamListener("input2")
    fun receiveInput2(receiveMsg: String) {
        println("input2 receive: $receiveMsg")
    }

}