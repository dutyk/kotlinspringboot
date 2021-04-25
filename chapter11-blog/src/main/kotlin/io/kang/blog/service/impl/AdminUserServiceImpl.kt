package io.kang.blog.service.impl

import io.kang.blog.entity.AdminUser
import io.kang.blog.repository.AdminUserDAO
import io.kang.blog.service.AdminUserService
import io.kang.blog.util.MD5Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminUserServiceImpl: AdminUserService {
    @Autowired
    lateinit var adminUserDAO: AdminUserDAO

    override fun login(userName: String, password: String): AdminUser? {
        val passwordMd5 = MD5Util.MD5Encode(password, "UTF-8")
        return adminUserDAO.login(userName, passwordMd5)
    }

    override fun getUserDetailById(loginUserId: Int): AdminUser? {
        return adminUserDAO.selectByPrimaryKey(loginUserId)
    }

    override fun updatePassword(loginUserId: Int, originalPassword: String, newPassword: String): Boolean {
        val adminUser = adminUserDAO.selectByPrimaryKey(loginUserId)
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            val originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8")
            val newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8")
            //比较原密码是否正确
            if (originalPasswordMd5 == adminUser.loginPassword) {
                //设置新密码并修改
                adminUser.loginPassword = newPasswordMd5
                if (adminUserDAO.updateByPrimaryKeySelective(adminUser) > 0) {
                    //修改成功则返回true
                    return true
                }
            }
        }
        return false
    }

    override fun updateName(loginUserId: Int, loginUserName: String, nickName: String): Boolean {
        val adminUser = adminUserDAO.selectByPrimaryKey(loginUserId)
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新密码并修改
            adminUser.loginUserName = loginUserName
            adminUser.nickName = nickName
            if (adminUserDAO.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true
            }
        }
        return false
    }
}