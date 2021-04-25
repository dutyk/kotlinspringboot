package io.kang.blog.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_link")
class BlogLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var linkId: Int? = null

    var linkType: Byte = 0

    var linkName: String = ""
        set(linkName) {
            field = linkName.trim { it <= ' ' }
        }

    var linkUrl: String = ""
        set(linkUrl) {
            field = linkUrl.trim { it <= ' ' }
        }

    var linkDescription: String = ""
        set(linkDescription) {
            field = linkDescription.trim { it <= ' ' }
        }

    var linkRank: Int = 0

    var isDeleted: Byte = 0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date = Date()

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, linkId=$linkId, linkType=$linkType, linkName=$linkName" +
                ", linkUrl=$linkUrl, linkDescription=$linkDescription, linkRank=$linkRank, isDeleted=$isDeleted, createTime=$createTime]"
    }
}