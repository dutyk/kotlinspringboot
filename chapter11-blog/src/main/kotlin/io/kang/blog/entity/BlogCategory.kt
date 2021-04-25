package io.kang.blog.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_blog_category")
class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoryId: Int = 0

    var categoryName: String = ""
        set(categoryName) {
            field = categoryName.trim { it <= ' ' }
        }

    var categoryIcon: String = ""
        set(categoryIcon) {
            field = categoryIcon.trim { it <= ' ' }
        }

    var categoryRank: Int = 0

    var isDeleted: Byte = 0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date = Date()

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, categoryId=$categoryId, categoryName=$categoryName, categoryIcon=$categoryIcon" +
                ", categoryRank=$categoryRank, isDeleted=$isDeleted, createTime=$createTime]"
    }
}