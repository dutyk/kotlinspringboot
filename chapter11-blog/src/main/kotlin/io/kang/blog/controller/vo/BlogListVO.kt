package io.kang.blog.controller.vo

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

class BlogListVO : Serializable {

    var blogId: Long? = null

    var blogTitle: String? = null

    var blogSubUrl: String? = null

    var blogCoverImage: String? = null

    var blogCategoryId: Int? = null

    var blogCategoryIcon: String? = null

    var blogCategoryName: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var createTime: Date? = null
}
