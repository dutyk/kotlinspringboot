package io.kang.blog.service.impl

import io.kang.blog.entity.BlogComment
import io.kang.blog.repository.BlogCommentDAO
import io.kang.blog.service.CommentService
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import java.util.*

@Service
class CommentServiceImpl: CommentService {
    @Autowired
    lateinit var blogCommentDAO: BlogCommentDAO

    override fun addComment(blogComment: BlogComment): Boolean {
        return blogCommentDAO.insertSelective(blogComment) > 0
    }

    override fun getCommentsPage(pageUtil: PageQueryUtil): PageResult {
        val comments = blogCommentDAO.findBlogCommentList(pageUtil)
        val total = blogCommentDAO.getTotalBlogComments(pageUtil)
        return PageResult(comments, total, pageUtil.limit, pageUtil.page)
    }

    override fun getTotalComments(): Int {
        return blogCommentDAO.getTotalBlogComments(null)
    }

    override fun checkDone(ids: List<Long>): Boolean {
        return blogCommentDAO.checkDone(ids) > 0
    }

    override fun deleteBatch(ids: List<Long>): Boolean {
        return blogCommentDAO.deleteBatch(ids) > 0
    }

    override fun reply(commentId: Long, replyBody: String): Boolean {
        val blogComment = blogCommentDAO.selectByPrimaryKey(commentId)
        //blogComment不为空且状态为已审核，则继续后续操作
        if (blogComment != null && blogComment.commentStatus?.toInt() === 1) {
            blogComment.replyBody = replyBody
            blogComment.replyCreateTime = Date()
            return blogCommentDAO.updateByPrimaryKeySelective(blogComment) > 0
        }
        return false
    }

    override fun getCommentPageByBlogIdAndPageNum(blogId: Long, page: Int): PageResult? {
        if (page < 1) {
            return null
        }
        val params = HashMap<String, Any?>()
        params["page"] = page
        //每页8条
        params["limit"] = 8
        params["blogId"] = blogId
        params["commentStatus"] = 1//过滤审核通过的数据
        val pageUtil = PageQueryUtil(params)
        val comments = blogCommentDAO.findBlogCommentList(pageUtil)
        if (!CollectionUtils.isEmpty(comments)) {
            val total = blogCommentDAO.getTotalBlogComments(pageUtil)
            return PageResult(comments, total, pageUtil.limit, pageUtil.page)
        }
        return null
    }
}