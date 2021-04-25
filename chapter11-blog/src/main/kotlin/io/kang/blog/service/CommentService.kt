package io.kang.blog.service

import io.kang.blog.entity.BlogComment
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult

interface CommentService {
    /**
     * 添加评论
     *
     * @param blogComment
     * @return
     */
    fun addComment(blogComment: BlogComment): Boolean

    /**
     * 后台管理系统中评论分页功能
     *
     * @param pageUtil
     * @return
     */
    fun getCommentsPage(pageUtil: PageQueryUtil): PageResult

    fun getTotalComments(): Int

    /**
     * 批量审核
     *
     * @param ids
     * @return
     */
    fun checkDone(ids: List<Long>): Boolean

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    fun deleteBatch(ids: List<Long>): Boolean

    /**
     * 添加回复
     *
     * @param commentId
     * @param replyBody
     * @return
     */
    fun reply(commentId: Long, replyBody: String): Boolean

    /**
     * 根据文章id和分页参数获取文章的评论列表
     *
     * @param blogId
     * @param page
     * @return
     */
    fun getCommentPageByBlogIdAndPageNum(blogId: Long, page: Int): PageResult?
}