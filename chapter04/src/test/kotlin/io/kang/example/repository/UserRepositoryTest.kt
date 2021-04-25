package io.kang.example.repository

import io.kang.example.entity.EducationLevel
import io.kang.example.entity.User
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class UserRepositoryTest {

    @Autowired
    lateinit var userReposiroty: UserRepository

    @Test
    @Order(1)
    fun testSaveUsers() {
        userReposiroty.deleteAll()
        var users = arrayOf(
                User(0, "test01", "test01", "test01@qq.com", 45, 175.5, "Shanghai", EducationLevel.BOSHI, 50000.0),
                User(1, "test02", "test02", "test02@qq.com", 36, 170.5, "Shanghai", EducationLevel.YANJIUSHENG, 20000.0),
                User(2, "test03", "test03", "test03@qq.com", 26, 165.5, "Shanghai", EducationLevel.YANJIUSHENG, 10000.0)
        )

        userReposiroty.saveAll(users.asList().asIterable())

        val users1 = userReposiroty.findAll()
        assertEquals(3, users1.toList().size)
    }

    @Test
    @Order(2)
    fun testFindByUserNameAndPassword() {
        val user = userReposiroty.findByUserNameAndPassword("test01", "test01")
        assertEquals("test01@qq.com", user?.email)
        assertEquals(45, user?.age)
        assertEquals(175.5, user?.height)
        assertEquals(EducationLevel.BOSHI, user?.education)
    }

    @Test
    @Order(3)
    fun testFindByUserNameLike() {
        val users = userReposiroty.findByUserNameLike("%test%")
        assertEquals(3, users?.size)
    }

    @Test
    @Order(4)
    fun testFindByIncomeGreaterThan() {
        val users = userReposiroty.findByIncomeGreaterThan(10000.0)
        assertEquals(2, users?.size)
    }

    @Test
    @Order(5)
    fun testFindByUserNameContains() {
        val users = userReposiroty.findByUserNameContains("test")
        assertEquals(3, users?.size)
    }

    @Test
    @Order(6)
    fun testDeleteByUserNameAndEmail() {
        userReposiroty.deleteByUserNameAndEmail("test01", "test01@qq.com")
        val users = userReposiroty.findAll().toList()
        assertEquals(2, users.size)
    }

    @Test
    @Order(7)
    fun testSave() {
        val user = User(3, "test04", "test04", "test04@qq.com", 26, 165.5, "Shanghai", EducationLevel.YANJIUSHENG, 10000.0)
        userReposiroty.save(user)
        val users = userReposiroty.findAll().toList()
        assertEquals(3, users.size)
    }

}