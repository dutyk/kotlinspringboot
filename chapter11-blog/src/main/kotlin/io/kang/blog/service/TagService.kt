package io.kang.blog.service

import io.kang.blog.entity.BlogTagCount
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult

interface TagService {
    /**
     * 查询标签的分页数据
     *
     * @param pageUtil
     * @return
     */
    fun getBlogTagPage(pageUtil: PageQueryUtil): PageResult

    fun getTotalTags(): Int

    fun saveTag(tagName: String): Boolean

    fun deleteBatch(ids: List<Int>): Boolean

    fun getBlogTagCountForIndex(): List<BlogTagCount>
}