package io.kang.chapter03.basicsyntax

fun main() {
    val items = listOf(1, 2, 3, 4, 5)
    // Lambdas 表达式是花括号括起来的代码块。
    items.fold(0, {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
        acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    })

    // lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })
    println(joinedToString)

    // 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)
    println(product)

    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // 类扩展调用

    var shapes: List<Rectangle> = List(3){ Rectangle() }
    var shapes1: List<Shape> = shapes

    var shapes2: MutableList<Rectangle> = MutableList(3){ Rectangle() }
    //var shapes3: MutableList<Shape> = shapes2
}

open class Shape
class Rectangle: Shape()
class Circle: Shape()
