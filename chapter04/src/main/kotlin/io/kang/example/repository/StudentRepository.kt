package io.kang.example.repository

import io.kang.example.entity.Student
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.DeleteQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface StudentRepository: MongoRepository<Student, Long> {
    fun findByPersonId(personId: Long): Student?

    fun findByNameRegexAndAgeGreaterThanEqual(name: String, age: Int, pageable: Pageable): Page<Student>

    @DeleteQuery(value = "{\"age\":{\"\$gte\":?0,\"\$lte\":?1}}")
    fun deleteByAgeIn(age1: Int, age2: Int)

    @Query(value = "{\"name\":{\"\$regex\":?0},\"age\":{\"\$gte\":?1}}")
    fun findByAgeIndividual(name: String ,age: Int, pageable: Pageable): Page<Student>
}