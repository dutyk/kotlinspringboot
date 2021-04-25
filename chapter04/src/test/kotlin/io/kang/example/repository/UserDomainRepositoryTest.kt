package io.kang.example.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.example.entity.EducationLevel
import io.kang.example.entity.QUser
import io.kang.example.entity.User
import org.junit.Assert
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class UserDomainRepositoryTest {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var userDomainRepository: UserDomainRepository

    @Test
    @Order(1)
    fun testSave() {
        userDomainRepository.deleteAll()
        var users = arrayOf(
                User(0, "test01", "test01", "test01@qq.com", 45, 175.5, "Shanghai", EducationLevel.BOSHI, 50000.0),
                User(1, "test02", "test02", "test02@qq.com", 36, 170.5, "Shanghai", EducationLevel.YANJIUSHENG, 20000.0),
                User(2, "test03", "test03", "test03@qq.com", 26, 165.5, "Shanghai", EducationLevel.YANJIUSHENG, 10000.0)
        )
        userDomainRepository.saveAll(users.asList().asIterable())
    }

    @Test
    @Order(2)
    fun testFindByUserNameAndPassword() {
        val qUser = QUser.user
        val predicate = qUser.userName.eq("test01").and(qUser.password.eq("test01"))
        val user = queryFactory.selectFrom(qUser).where(predicate).fetchOne()
        Assert.assertEquals("test01@qq.com", user?.email)
        Assert.assertEquals(45, user?.age)
        Assert.assertEquals(175.5, user?.height)
        Assert.assertEquals(EducationLevel.BOSHI, user?.education)
    }

    @Test
    @Order(3)
    fun testFindByUserNameLike() {
        val qUser = QUser.user
        val predicate = qUser.userName.like("%test%")
        val users = queryFactory.selectFrom(qUser).where(predicate).fetch()
        Assert.assertEquals(3, users?.size)
    }

    @Test
    @Order(4)
    fun testFindByIncomeGreaterThan() {
        val qUser = QUser.user
        val predicate = qUser.income.gt(10000.0)
        val users = queryFactory.selectFrom(qUser).where(predicate).fetch()
        Assert.assertEquals(2, users.size)
    }

    @Test
    @Order(5)
    fun testFindByUserNameContains() {
        val qUser = QUser.user
        val predicate = qUser.userName.contains("test")
        val users = queryFactory.selectFrom(qUser).where(predicate).fetch()
        Assert.assertEquals(3, users.size)
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(false)
    fun testDeleteByUserNameAndEmail() {
        val qUser = QUser.user
        val predicate = qUser.userName.eq("test01").and(qUser.email.eq("test01@qq.com"))
        queryFactory.delete(qUser).where(predicate).execute()
        val users = queryFactory.selectFrom(qUser).fetch()
        Assert.assertEquals(2, users.size)
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(false)
    fun testUpdateEmailByUserName() {
        val qUser = QUser.user
        val predicate = qUser.userName.eq("test02")
        queryFactory.update(qUser).set(qUser.email, "test02@yy.com").where(predicate).execute()
        val user = queryFactory.selectFrom(qUser).where(predicate).fetchOne()
        Assert.assertEquals("test02@yy.com", user?.email)
    }
}