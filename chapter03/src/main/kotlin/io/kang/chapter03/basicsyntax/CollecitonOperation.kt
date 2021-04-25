package io.kang.chapter03.basicsyntax

import java.lang.StringBuilder
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    val filterResults = mutableListOf<String>()  //destination object
    numbers.filterTo(filterResults) { it.length > 3 }
    println(filterResults) // contains results of both operations

    //map
    val numbers1 = setOf(1, 2, 3)
    println(numbers1.map { it * 3 })
    println(numbers1.mapIndexed { idx, value -> value * idx })

    println(numbers1.mapNotNull { if ( it == 2) null else it * 3 })
    println(numbers1.mapIndexedNotNull { idx, value -> if (idx == 0) null else value * idx })

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    println(numbersMap.mapKeys { it.key.toUpperCase() })
    println(numbersMap.mapValues { it.value + it.key.length })

    //zip

    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors zip animals)

    val twoAnimals = listOf("fox", "bear")
    println(colors.zip(twoAnimals))
    println(colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color"})

    val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    println(numberPairs.unzip())

    //associate
    val numbers2 = listOf("one", "two", "two", "four")
    println(numbers2.associateWith { it.length })

    println(numbers2.associateBy { it.first().toUpperCase() })
    println(numbers2.associateBy(keySelector = { it.first().toUpperCase() }, valueTransform = { it.length }))

    //flattern
    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    println(numberSets.flatten())

    val containers = listOf(
            (listOf("one", "two", "three")).associateWith { it.length },
            (listOf("four", "five", "six")).associateWith { it.length })
    println(containers.flatMap { it.keys })

    //zifuchuan

    val numbers3 = listOf("one", "two", "three", "four")

    println(numbers3)
    println(numbers3.joinToString())

    val listString = StringBuffer("The list of numbers: ")
    numbers3.joinTo(listString)
    println(listString)

    //filter
    val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numbersMap1.filter { (key, value) -> key.endsWith("1") && value > 10}
    println(filteredMap)

    val numbers4 = listOf("one", "two", "three", "four")

    val filteredIdx = numbers4.filterIndexed { index, s -> (index != 0) && (s.length < 5)  }
    val filteredNot = numbers4.filterNot { it.length <= 3 }

    println(filteredIdx)
    println(filteredNot)

    //plus minus

    val numbers5 = listOf("one", "two", "three", "four")

    val plusList = numbers5 + "five"
    val minusList = numbers5 - listOf("three", "four")
    println(plusList)
    println(minusList)

    //group

    val numbers6 = listOf("one", "two", "three", "four", "five")

    println(numbers6.groupBy { it.first().toUpperCase() })
    println(numbers6.groupBy(keySelector = { it.first() }, valueTransform = { it.toUpperCase() }))
    
    //slice
    val numbers7 = listOf("one", "two", "three", "four", "five", "six")
    println(numbers7.slice(1..3))
    println(numbers7.slice(0..4 step 2))
    println(numbers7.slice(setOf(3, 5, 0)))

    //take,drop
    val numbers8 = listOf("one", "two", "three", "four", "five", "six")
    println(numbers8.take(3))
    println(numbers8.takeLast(3))
    println(numbers8.drop(1))
    println(numbers8.dropLast(5))

    println(numbers8.takeWhile { !it.startsWith('f') })
    println(numbers8.takeLastWhile { it != "three" })
    println(numbers8.dropWhile { it.length == 3 })
    println(numbers8.dropLastWhile { it.contains('i') })

    //chunked
    val numbers9 = (0..13).toList()
    println(numbers9.chunked(3))
    println(numbers9.chunked(3) { it.sum() })  // `it` is a chunk of the original collection

    //windowed
    val numbers10 = (1..10).toList()
    println(numbers10.windowed(3))
    println(numbers10.windowed(3, step = 2, partialWindows = true))
    println(numbers10.windowed(3) { it.sum() })
    println(numbers10.zipWithNext())
    println(numbers10.zipWithNext() { s1, s2 -> s1 + s2})

    //sorted
    class Version(val major: Int, val minor: Int): Comparable<Version> {
        override fun compareTo(other: Version): Int {
            if (this.major != other.major) {
                return this.major - other.major
            } else if (this.minor != other.minor) {
                return this.minor - other.minor
            } else return 0
        }
    }
    println(Version(1, 2) > Version(1, 3))
    println(Version(2, 0) > Version(1, 5))

    val unSortednumbers = listOf("one", "two", "three", "four")

    val lengthComparator = Comparator { str1: String, str2: String -> str1.length - str2.length }
    println(unSortednumbers.sortedWith(lengthComparator))
    println(unSortednumbers.sortedWith(compareBy { it.length }))


    println("Sorted ascending: ${unSortednumbers.sorted()}")
    println("Sorted descending: ${unSortednumbers.sortedDescending()}")

    val sortedNumbers = unSortednumbers.sortedBy { it.length }
    println("Sorted by length ascending: $sortedNumbers")
    val sortedByLast = unSortednumbers.sortedByDescending { it.last() }
    println("Sorted by the last letter descending: $sortedByLast")
    println(unSortednumbers.reversed())
    println(unSortednumbers.shuffled())

    //merge
    val numbersMerge = listOf(6, 42, 10, 4)

    println("Count: ${numbersMerge.count()}")
    println("Max: ${numbersMerge.max()}")
    println("Min: ${numbersMerge.min()}")
    println("Average: ${numbersMerge.average()}")
    println("Sum: ${numbersMerge.sum()}")
    val min3Remainder = numbersMerge.minBy { it % 3 }
    println(min3Remainder)
    val max3Remainder = numbersMerge.maxWith(compareBy { it - 10 })
    println(max3Remainder)
    println(numbersMerge.sumBy { it * 2 })
    println(numbersMerge.sumByDouble { it.toDouble() / 2 })

    val sum = numbersMerge.reduce { sum, element -> sum + element }
    println(sum)
    val sumDoubled = numbersMerge.fold(0) { sum, element -> sum + element * 2 }
    println(sumDoubled)

    //list operation
    val listNuumbers = listOf(1, 2, 3, 2)
    println(listNuumbers.get(0))
    println(listNuumbers[0])
    //numbers.get(5)                         // exception!
    println(listNuumbers.getOrNull(5))             // null
    println(listNuumbers.getOrElse(5, {it}))        // 5
    println(listNuumbers.subList(0, 2))
    println(listNuumbers.indexOf(2))
    println(listNuumbers.lastIndexOf(2))
    println(listNuumbers.indexOfFirst { it > 2})
    println(listNuumbers.indexOfLast { it % 2 == 1})

    listNuumbers.sorted()
    println(listNuumbers.binarySearch(2))  // 3
    println(listNuumbers.binarySearch(4)) // -5
    println(listNuumbers.binarySearch(1, 0, 2))  // -3

    //set
    val setNnumbers = setOf("one", "two", "three")

    println(setNnumbers union setOf("four", "five"))

    println(setNnumbers intersect setOf("two", "one"))
    println(setNnumbers subtract setOf("three", "four"))
    println(setNnumbers subtract setOf("four", "three")) // same output

    //map
    val mapNumbers = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(mapNumbers.get("one"))
    println(mapNumbers["one"])
    println(mapNumbers.getOrDefault("four", 10))
    println(mapNumbers["five"])               // null

    println(mapNumbers.keys)
    println(mapNumbers.values)
    println(mapNumbers.filter { (key, value) -> key.endsWith("1") && value > 10})
    println(mapNumbers.filterKeys { it.endsWith("1") })
    println(mapNumbers.filterValues { it < 10 })
    println(mapNumbers + Pair("four", 4))
    println(mapNumbers + Pair("one", 10))
    println(mapNumbers + mapOf("five" to 5, "one" to 11))
    println(mapNumbers - "one")
    println(mapNumbers - listOf("two", "four"))

}