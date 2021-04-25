package io.kang.blog.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.annotation.Generated
import javax.persistence.*

@Entity
@Table(name = "tb_blog_comment")
class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Long? = null

    var blogId: Long = 0

    var commentator: String = ""
        set(commentator) {
            field = commentator.trim { it <= ' ' }
        }

    var email: String = ""
        set(email) {
            field = email.trim { it <= ' ' }
        }

    var websiteUrl: String = ""
        set(websiteUrl) {
            field = websiteUrl.trim { it <= ' ' }
        }

    var commentBody: String = ""
        set(commentBody) {
            field = commentBody.trim { it <= ' ' }
        }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var commentCreateTime: Date = Date()

    var commentatorIp: String = ""
        set(commentatorIp) {
            field = commentatorIp.trim { it <= ' ' }
        }

    var replyBody: String = ""
        set(replyBody) {
            field = replyBody.trim { it <= ' ' }
        }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var replyCreateTime: Date = Date()

    var commentStatus: Byte = 0

    var isDeleted: Byte = 0

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, commentId=$commentId, blogId=$blogId, commentator=$commentator" +
                ", email=$email, websiteUrl=$websiteUrl, commentBody=$commentBody, commentCreateTime=$commentCreateTime" +
                ", commentatorIp=$commentatorIp, replyBody=$replyBody, replyCreateTime=$replyCreateTime, " +
                "commentStatus=$commentStatus, isDeleted=$isDeleted]"
    }
}