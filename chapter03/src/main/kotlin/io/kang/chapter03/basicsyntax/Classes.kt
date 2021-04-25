package io.kang.chapter03.basicsyntax

class Person(val name: String, var age: Int = 0) {
    var children: MutableList<Person> = mutableListOf<Person>();

    init {
        println("name is $name, age is $age")
    }

    constructor(name: String, age: Int, parent: Person) : this(name, age) {
        parent.children.add(this)
    }
}

fun main() {
    val person = Person("yuan")
    val parentPerson = Person("kang", 1, person)

    val derived = Derived("kang", 1)
    derived.draw()

    val demo = Demo(MutableList<String>(10){"a"})
    println(demo.size)
    println(demo.listToString)

    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    val (name, age) = jack
    println("$name, $age years of age") // 输出 "Jane, 35 years of age"

    copyValue()

    val arrayNulls = arrayOfNulls<Any>(2)
    fill(arrayNulls, "hello")
    println(arrayNulls.asList())
}


open class Base(val p: Int) {
    open val vertexCount: Int = 0
    open fun draw() {
        println("Base.draw()")
    }
    constructor(base: String, p: Int): this(p){
        println("base is $base")
    }
}
class Derived: Base {
    override var vertexCount = 4
    constructor(base: String, p: Int): super(base, p)

    override fun draw() {
        println("Derived.draw()")
    }
}

class Demo(val aList: MutableList<String>) {
    var size: Int = 0
        get() = aList.size
        set(value) {
            field = value
        }
    var listToString: String
        get() = aList.toString()
        set(value) {
            aList.add(value)
        }
}

interface Named {
    val name: String
}

interface People : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName $lastName"
}

class Employee(
        // 不必实现“name”
        override val firstName: String,
        override val lastName: String
) : People


data class User(val name: String = "", val age: Int = 0)

sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()

fun eval(expr: Expr): Double = when(expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    // 不再需要 `else` 子句，因为我们已经覆盖了所有的情况
}

enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

class Box<T>(t: T) {
    var value = t
}
val box: Box<Int> = Box<Int>(1)

interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // 这个没问题，因为 T 是一个 out-参数
}

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
    // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
    val y: Comparable<Double> = x // OK！
}

fun copy(from: Array<out Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}
fun copyValue() {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    copy(ints, any)
    println(any.asList())
    //   ^ 其类型为 Array<Int> 但此处期望 Array<Any>
}

fun fill(dest: Array<in String>, value: String) {
    for(i in dest.indices){
        dest[i] = value
    }
}