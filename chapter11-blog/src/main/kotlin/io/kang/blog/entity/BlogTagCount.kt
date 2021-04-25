package io.kang.blog.entity

class BlogTagCount {
    var tagId: Int = 0

    var tagName: String = ""

    var tagCount: Int = 0

    override fun toString(): String {
        return "tagId:$tagId, tagName:$tagName, tagCount:$tagCount"
    }
}