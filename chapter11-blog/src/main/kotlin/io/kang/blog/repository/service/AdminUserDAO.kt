package io.kang.blog.repository

import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.AdminUser
import io.kang.blog.entity.QAdminUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AdminUserDAO {
    @Autowired
    lateinit var adminUserRepository: AdminUserRepository

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Transactional
    fun insert(record: AdminUser): Int {
        adminUserRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: AdminUser): Int {
        //todo how to shixian insert selective
        adminUserRepository.save(record)
        return 0
    }

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    fun login(userName: String, password: String): AdminUser? {
        val qAdminUser = QAdminUser.adminUser
        return queryFactory.selectFrom(qAdminUser)
                .where(qAdminUser.loginUserName.eq(userName).and(qAdminUser.loginPassword.eq(password)))
                .fetchOne()
    }

    fun selectByPrimaryKey(adminUserId: Int): AdminUser? {
        val qAdminUser = QAdminUser.adminUser

        return queryFactory.selectFrom(qAdminUser)
                .where(qAdminUser.adminUserId.eq(adminUserId))
                .fetchOne()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: AdminUser): Int {
        val qAdminUser = QAdminUser.adminUser

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.loginUserName != null) {
            cols.add(qAdminUser.loginUserName)
            values.add(record.loginUserName)
        }

        if(record.loginPassword != null) {
            cols.add(qAdminUser.loginPassword)
            values.add(record.loginPassword)
        }

        if(record.nickName != null) {
            cols.add(qAdminUser.nickName)
            values.add(record.nickName)
        }

        if(record.locked != null) {
            cols.add(qAdminUser.locked)
            values.add(record.locked)
        }

        return queryFactory.update(qAdminUser)
                .set(cols, values)
                .where(qAdminUser.adminUserId.eq(record.adminUserId))
                .execute()
                .toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: AdminUser): Int {
        val qAdminUser = QAdminUser.adminUser

        return queryFactory.update(qAdminUser)
                .set(qAdminUser.loginUserName, record.loginUserName)
                .set(qAdminUser.loginPassword, record.loginPassword)
                .set(qAdminUser.nickName, record.nickName)
                .set(qAdminUser.locked, record.locked)
                .where(qAdminUser.adminUserId.eq(record.adminUserId))
                .execute()
                .toInt()
    }

}