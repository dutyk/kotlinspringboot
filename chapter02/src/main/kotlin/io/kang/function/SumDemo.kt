package io.kang.function

fun main() {
    val nums = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 10)

    println(nums.fold(0, { acc, i ->  acc + i}))
}