package io.kang.blog.entity

import javax.persistence.*

@Entity
@Table(name = "tb_admin_user")
class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var adminUserId: Int? = null

    var loginUserName: String = ""
        set(loginUserName) {
            field = loginUserName?.trim { it <= ' ' }
        }

    var loginPassword: String = ""
        set(loginPassword) {
            field = loginPassword?.trim { it <= ' ' }
        }

    var nickName: String = ""
        set(nickName) {
            field = nickName?.trim { it <= ' ' }
        }

    var locked: Byte = 0

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, adminUserId=$adminUserId, loginUserName=$loginUserName" +
                ", loginPassword=$loginPassword, nickName=$nickName, locked=$locked]"
    }
}