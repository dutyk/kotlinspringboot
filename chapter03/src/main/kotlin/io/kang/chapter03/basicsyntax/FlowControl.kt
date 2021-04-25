package io.kang.chapter03.basicsyntax

fun main() {
    // 传统用法
    var maxVal = 1
    if (1 < 2) maxVal = 2

    // With else
    if (1 > 2) {
        maxVal = 1
    } else {
        maxVal = 2
    }

    // 作为表达式
    maxVal = if (1 > 2) 1 else 2

    maxVal = if (1 > 2) {
        print("Choose 1")
        1
    } else {
        print("Choose 2")
        2
    }

    val x = 0
    when (0) {
        is Int -> println("x is Int")
        else -> println("other type")
    }

    for (i in 1..3) {
        println(i)
    }
    for (i in 6 downTo 0 step 2) {
        println(i)
    }


    val array = arrayOf(1,2,3)
    for (i in array.indices) {
        println(array[i])
    }

    for ((index, value) in array.withIndex()) {
        println("the element at $index is $value")
    }


    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // 非局部直接返回到 foo() 的调用者
            print(it)
        }
        println("this point is unreachable")
    }

    fun foo1() {
        loop@ for (i in 1..5) {
            for (j in 1..5) {
                if (j == 2) break@loop
                println("$i---$j")
            }
        }
    }

    fun foo2() {
        loop@ for (i in 1..5) {
            for (j in 1..5) {
                if (j == 2) continue@loop
                println("$i---$j")
            }
        }
    }

    foo()
    foo1()
    foo2()

}