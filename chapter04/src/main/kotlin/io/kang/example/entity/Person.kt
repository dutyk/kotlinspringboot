package io.kang.example.entity

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.Id

open class Person(
        @Id
        var personId: Long = 0L,
        var name: String = "",
        var address: String = "",
        var age: Int = 0,
        var date: Date = Date()
)

@Document(collection="student")
data class Student(
        val likeSport: String,
        val likeBook: String,
        val school: String
): Person()