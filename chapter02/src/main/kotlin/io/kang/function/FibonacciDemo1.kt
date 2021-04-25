package io.kang.function;

import java.util.stream.Stream
import kotlin.streams.toList

fun fibonacci(num: Int): Int {
    return Stream.iterate(listOf(1, 1), {
                t ->  listOf(t[1], t[0] + t[1])
            })
            .limit(num.toLong())
            .map { n -> n[1] }
            .toList()[num - 1]

}
fun main() {
    print(fibonacci(8))
}
