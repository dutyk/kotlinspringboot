package io.kang.blog.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_blog")
class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var blogId: Long = 0

    var blogTitle: String = ""
        set(blogTitle) {
            field = blogTitle.trim { it <= ' ' }
        }

    var blogSubUrl: String = ""
        set(blogSubUrl) {
            field = blogSubUrl.trim { it <= ' ' }
        }

    var blogCoverImage: String = ""
        set(blogCoverImage) {
            field = blogCoverImage.trim { it <= ' ' }
        }

    var blogCategoryId: Int = 0

    var blogCategoryName: String = ""
        set(blogCategoryName) {
            field = blogCategoryName.trim { it <= ' ' }
        }

    var blogTags: String = ""
        set(blogTags) {
            field = blogTags.trim { it <= ' ' }
        }

    var blogStatus: Byte = 0

    var blogViews: Long = 0

    var enableComment: Byte = 0

    var isDeleted: Byte = 0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date = Date()

    var updateTime: Date = Date()

    var blogContent: String = ""
        set(blogContent) {
            field = blogContent.trim { it <= ' ' }
        }

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, blogId=$blogId, blogTitle=$blogTitle, blogSubUrl=$blogSubUrl" +
                ", blogCoverImage=$blogCoverImage, blogCategoryId=$blogContent, blogCategoryName=$blogCategoryName, blogTags=$blogTags" +
                ", blogStatus=$blogStatus, blogViews=$blogViews, enableComment=$enableComment, isDeleted=$isDeleted" +
                ", createTime=$createTime, updateTime=$updateTime, blogContent=$blogContent]"
    }
}