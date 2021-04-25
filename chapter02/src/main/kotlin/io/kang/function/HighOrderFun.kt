package io.kang.function


fun test(a: Int , b: Int, add: (num1 : Int , num2 : Int) -> Int) : Int{
    return add(a, b)
}

fun main() {
    // 调用
    println(test(10, 11) { num1: Int, num2: Int -> num1 + num2})
}