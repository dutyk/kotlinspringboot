package io.kang.blog.service

import io.kang.blog.entity.AdminUser

interface AdminUserService {
    fun login(userName: String, password: String): AdminUser?

    /**
     * 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    fun getUserDetailById(loginUserId: Int): AdminUser?

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    fun updatePassword(loginUserId: Int, originalPassword: String, newPassword: String): Boolean

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    fun updateName(loginUserId: Int, loginUserName: String, nickName: String): Boolean
}