package io.kang.chapter03.basicsyntax

fun main() {
    //List
    val bob = Person("Bob", 31)
    val people = listOf<Person>(bob, bob, Person("Bob", 31))
    val people2 = listOf<Person>(bob, bob, bob)
    println(people == people2)
    bob.age = 32
    println(people == people2)


    //MutableList
    val numbers = mutableListOf(1, 2, 3, 4)
    numbers.add(5)
    numbers.removeAt(1)
    numbers[0] = 0
    numbers.shuffle()
    println(numbers)

    //Set
    println("Number of elements: ${numbers.size}")
    if (numbers.contains(1)) println("1 is in the set")

    val numbersBackwards = setOf(4, 3, 2, 1)
    println("The sets are equal: ${numbers == numbersBackwards}")

    // LinkedHashSet is the default implementation
    println(numbers.first() == numbersBackwards.first())
    println(numbers.first() == numbersBackwards.last())

    //Map

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)

    println("All keys: ${numbersMap.keys}")
    println("All values: ${numbersMap.values}")
    if ("key2" in numbersMap) println("Value by key \"key2\": ${numbersMap["key2"]}")
    if (1 in numbersMap.values) println("The value 1 is in the map")
    if (numbersMap.containsValue(1)) println("The value 1 is in the map") // 同上

    val anotherMap = mapOf("key2" to 2, "key1" to 1, "key4" to 1, "key3" to 3)

    println("The maps are equal: ${numbersMap == anotherMap}")

    val mutableNumbersMap = mutableMapOf("one" to 1, "two" to 2)
    mutableNumbersMap.put("three", 3)
    mutableNumbersMap["one"] = 11

    println(mutableNumbersMap)

    //construct collection
    val numbersSet = setOf("one", "two", "three", "four")
    val emptySet = mutableSetOf<String>()
    val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    val numbersMap2 = mutableMapOf<String, String>().apply { this["one"] = "1"; this["two"] = "2" }
    val empty = emptyList<String>()

    val doubled = List(3, { it * 2 })  // 如果你想操作这个集合，应使用 MutableList
    println(doubled)

    //copy
    val sourceList = mutableListOf(1, 2, 3)
    val copyList = sourceList.toMutableList()
    sourceList.add(4)
    println("Copy size: ${copyList.size}")

    val readOnlyCopyList = sourceList.toList()
    //readOnlyCopyList.add(4)             // 编译异常
    println("Read-only copy size: ${readOnlyCopyList.size}")

    //transform
    val copySet = sourceList.toMutableSet()
    copySet.add(3)
    copySet.add(4)
    println(copySet)

    val referenceList: List<Int> = sourceList
    //referenceList.add(4)            // 编译错误
    sourceList.add(4)
    println(referenceList) // 显示 sourceList 当前状态

    //iterator
    val numberStrs = listOf("one", "two", "three", "four")
    numberStrs.forEach {
        println(it)
    }

    for (item in numberStrs) {
        println(item)
    }

    numberStrs.forEach {
        println(it)
    }

    //mutableIterator
    val numberStrs1 = mutableListOf("one", "two", "three", "four")
    val mutableIterator = numberStrs1.iterator()

    mutableIterator.next()
    mutableIterator.remove()
    println("After removal: $numberStrs1")

    //
    val mutableListIterator = numberStrs1.listIterator()

    mutableListIterator.next()
    mutableListIterator.add("two")
    mutableListIterator.next()
    mutableListIterator.set("three")
    println(numberStrs1)


    for (i in 1 until 10 step 2) {       // i in [1, 10), 10 is excluded
        print(i)
    }

    val rangeInt = 1..10 step 2
    println(3 in rangeInt)
    println(2 !in rangeInt)


    val numbersSequence = sequenceOf("four", "three", "two", "one")
    val numbersSequence1 = numbers.asSequence()

    //函数构建
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    println(oddNumbers.take(5).toList())

    //限长序列
    val oddNumbersLessThan10 = generateSequence(1) { if (it < 10) it + 2 else null }
    println(oddNumbersLessThan10.toList())

    val oddNumbers1 = sequence {
        yield(1)
        yieldAll(listOf(3, 5))
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers1.take(5).toList())


    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    //convert the List to a Sequence
    val wordsSequence = words.asSequence()

    val lengthsSequence = wordsSequence.filter { println("filter: $it"); it.length > 3 }
            .map { println("length: ${it.length}"); it.length }
            .take(4)

    println("Lengths of first 4 words longer than 3 chars")
    // terminal operation: obtaining the result as a List
    println(lengthsSequence.toList())

}