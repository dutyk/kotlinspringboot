package io.kang.chapter03.basicsyntax

open class A {
    open fun foo(i: Int = 10) { /*……*/ }
}

class B : A() {
    override fun foo(i: Int) { /*……*/ }  // 不能有默认值
}

fun foo(bar: Int = 0, baz: Int) { /*……*/ }

fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit): Unit { /*……*/ }

//单表达式函数
fun double(x: Int) = x * 2

fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

data class Vertex(val neighbors: List<Vertex>)
data class Graph(val vertices: List<Vertex>)
fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}

val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))
fun main() {
    foo(baz = 1) // 使用默认值 bar = 0

    foo(1) { println("hello") }     // 使用默认值 baz = 1
    foo(qux = { println("hello") }) // 使用两个默认值 bar = 0 与 baz = 1
    foo { println("hello") }        // 使用两个默认值 bar = 0 与 baz = 1

    //可以用*将数组伸展开
    val a = arrayOf(1, 2, 3)
    val list = asList(-1, 0, *a, 4)

    1 shl 2
}