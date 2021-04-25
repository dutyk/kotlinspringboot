package io.kang.blog.service

import io.kang.blog.controller.vo.BlogDetailVO
import io.kang.blog.controller.vo.SimpleBlogListVO
import io.kang.blog.entity.Blog
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult

interface BlogService {
    fun saveBlog(blog: Blog): String

    fun getBlogsPage(pageUtil: PageQueryUtil): PageResult

    fun deleteBatch(ids: List<Long>): Boolean

    fun getTotalBlogs(): Int

    /**
     * 根据id获取详情
     *
     * @param blogId
     * @return
     */
    fun getBlogById(blogId: Long): Blog

    /**
     * 后台修改
     *
     * @param blog
     * @return
     */
    fun updateBlog(blog: Blog): String

    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    fun getBlogsForIndexPage(page: Int): PageResult

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     *
     * @param type
     * @return
     */
    fun getBlogListForIndexPage(type: Int): List<SimpleBlogListVO>

    /**
     * 文章详情
     *
     * @param blogId
     * @return
     */
    fun getBlogDetail(blogId: Long): BlogDetailVO?

    /**
     * 根据标签获取文章列表
     *
     * @param tagName
     * @param page
     * @return
     */
    fun getBlogsPageByTag(tagName: String, page: Int): PageResult?

    /**
     * 根据分类获取文章列表
     *
     * @param categoryId
     * @param page
     * @return
     */
    fun getBlogsPageByCategory(categoryId: String, page: Int): PageResult?

    /**
     * 根据搜索获取文章列表
     *
     * @param keyword
     * @param page
     * @return
     */
    fun getBlogsPageBySearch(keyword: String, page: Int): PageResult?

    fun getBlogDetailBySubUrl(subUrl: String): BlogDetailVO?
}