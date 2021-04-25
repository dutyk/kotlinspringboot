package io.kang.chapter03.basicsyntax

import jdk.nashorn.internal.objects.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlin.system.*

//fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
//
//fun foo(): Flow<Int> = flow {
//    for (i in 1..3) {
//        Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
//        log("Emitting $i")
//        emit(i) // 发射下一个值
//    }
//}.flowOn(Dispatchers.Default) // 在流构建器中改变消耗 CPU 代码上下文的正确方式
//
//data class Ball(var hits: Int)
//
//fun main() = runBlocking {
//    val table = Channel<Ball>() // 一个共享的 table（桌子）
//    launch { player("ping", table) }
//    launch { player("pong", table) }
//    table.send(Ball(0)) // 乒乓球
//    delay(600) // 延迟 1 秒钟
//    coroutineContext.cancelChildren() // 游戏结束，取消它们
//}
//
//suspend fun player(name: String, table: Channel<Ball>) {
//    for (ball in table) { // 在循环中接收球
//        ball.hits++
//        println("$name $ball")
//        delay(300) // 等待一段时间
//        table.send(ball) // 将球发送回去
//    }
//}


fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}
fun main() = runBlocking {
    val squares = produceSquares()
    // 打印：
    squares.consumeEach { println(it) }
    println("Done!")
}
