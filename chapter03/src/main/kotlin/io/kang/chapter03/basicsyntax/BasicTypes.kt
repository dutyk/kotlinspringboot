package io.kang.chapter03.basicsyntax

fun main() {
    fun printDouble(d: Double) { print(d) }

    val i = 1
    val d = 1.1
    val f = 1.1f

    printDouble(d)
//    printDouble(i) // Error: Type mismatch
//    printDouble(f) // Error: Type mismatch

    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010


    val a: Int = 10000
    println(a === a) // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA) // !!!Prints 'false'!!!
    println(boxedA == anotherBoxedA) //Prints 'true'

    val x = 1.01
    val isInRange = x in 1.0..2.0
    val isNotInRange = x !in 0.0..1.0
    println(isInRange) //Prints 'true'
    println(isNotInRange) //Prints 'true'

    val arrayTemp = arrayOf(1,2,3)
    arrayTemp[0] = 0
    println(arrayTemp[0]) //Prints '0'
    println(arrayTemp.size) //Prints '3'

    val intArray: IntArray = intArrayOf(1, 2, 3)
    val byteArray: ByteArray = byteArrayOf(1, 2, 3)
    val shortArray: ShortArray = shortArrayOf(1, 2, 3)
    val longArray: LongArray = longArrayOf(1, 2, 3)
    val charArray: CharArray = charArrayOf('1', '2', '3')
    val floatArray: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f)
    val doubleArray: DoubleArray = doubleArrayOf(1.0, 2.0, 3.0)
    val booleanArray: BooleanArray = booleanArrayOf(true, false, true)

    val str = "hello"
    for (c in str) {
        println(c)
    }

    val s = "abc" + 1
    println(s + "def")

    //        for (c in "foo")
    //        print(c)
    val text = """
        for (c in "foo")
        print(c)
        """
    println(text)

    //Tell me and I forget.
    //Teach me and I remember.
    //Involve me and I learn.
    //(Benjamin Franklin)
    val text1 = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
    println(text1)

    val strTemplate = "abc"
    println("$strTemplate.length is ${strTemplate.length}") // prints "abc.length is 3"

    val price = """
        |${'$'}9.99
        """.trimMargin()
    println(price) //prints $9.99



}