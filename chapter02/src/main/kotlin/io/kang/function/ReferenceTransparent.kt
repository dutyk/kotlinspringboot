package io.kang.function

fun main() {
    val xs = listOf(1, 2, 3, 4, 5)

    //多次调用返回结果一致
    println(xs.slice(IntRange(0, 3)))
    println(xs.slice(IntRange(0, 3)))
    println(xs.slice(IntRange(0, 3)))

    var xs1 = mutableListOf(1, 2, 3)

    //多次调用返回结果不一样 xs1
    xs1.add(4)
    println(xs1)
    xs1.add(5)
    println(xs1)
    xs1.add(6)
    println(xs1)
}