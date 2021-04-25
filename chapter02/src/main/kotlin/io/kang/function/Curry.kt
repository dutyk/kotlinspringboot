package io.kang.function

fun main() {
    //curry
    fun add(a: Int): (b: Int) -> (c: Int) -> Int {
        return {b -> {c ->  a + b * c} }
    }
    println(add(1)(2)(3))

    //lazy
    val y = lazy {
        println("y")
        13
    }

    println(y)
    println(y.value)
}