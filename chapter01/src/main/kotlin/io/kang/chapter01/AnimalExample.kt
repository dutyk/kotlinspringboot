package io.kang.chapter01

import javafx.application.Application.launch

//1 数据类
data class Animal(val name: String,
        //2 可为空的类型(Int?),声明变量的默认值
                  val age: Int? = null)

//3 顶层函数
fun main(args: Array<String>) {
    //4 命名声明
    val animals = listOf(Animal("Dog", 2), Animal("Cat", 3))

    //5 lambda表达式
    val oldest = animals.maxBy { it.age ?: 0 }
    //6 字符串模板
    println("The Oldest is $oldest")

    //7 自动生成`toString`方法
    //The Oldest is Animal(name=Cat, age=3)
}