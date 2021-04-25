package io.kang.example.entity;

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val userName: String,
        val password: String,
        val email: String,
        val age: Int,
        val height: Double,
        val address: String,
        val education: EducationLevel,
        val income: Double
)
enum class EducationLevel {
    XIAOXUE, GAOZHONG, BENKE, YANJIUSHENG, BOSHI
}
