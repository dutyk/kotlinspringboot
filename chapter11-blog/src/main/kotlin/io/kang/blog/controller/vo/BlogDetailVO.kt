package io.kang.blog.controller.vo

import java.util.*

class BlogDetailVO {
    var blogId: Long? = null

    var blogTitle: String? = null

    var blogCategoryId: Int? = null

    var commentCount: Int? = null

    var blogCategoryIcon: String? = null

    var blogCategoryName: String? = null

    var blogCoverImage: String? = null

    var blogViews: Long? = null

    var blogTags: List<String>? = null

    var blogContent: String? = null

    var enableComment: Byte? = null

    var createTime: Date? = null
}
