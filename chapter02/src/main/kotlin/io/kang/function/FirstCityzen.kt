package io.kang.function

fun main() {
    val arr = listOf(1, 2, 3, 4)
    //作为参数
    println(arr.reduce { acc, i -> acc + i })

    //作为返回值
    fun operate(num: Int): (Int) -> Int {
        return {num -> num * 2}
    }

    //匿名函数
    fun(x: Int, y: Int) = x + y
}