package io.kang.blog.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.BooleanOperation
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.BlogComment
import io.kang.blog.entity.QBlogComment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogCommentDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogCommentRepository: BlogCommentRepository

    @Transactional
    fun deleteByPrimaryKey(commentId: Long): Int {
        val qBlogComment = QBlogComment.blogComment

        return queryFactory.update(qBlogComment)
                .set(qBlogComment.isDeleted, 1)
                .where(qBlogComment.commentId.eq(commentId).and(qBlogComment.isDeleted.eq(0)))
                .execute()
                .toInt()
    }

    @Transactional
    fun insert(record: BlogComment): Int {
        blogCommentRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: BlogComment): Int {
        blogCommentRepository.save(record)
        return 0
    }

    fun selectByPrimaryKey(commentId: Long): BlogComment? {
        val qBlogComment = QBlogComment.blogComment

        return queryFactory.selectFrom(qBlogComment)
                .where(qBlogComment.commentId.eq(commentId).and(qBlogComment.isDeleted.eq(0)))
                .fetchOne()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: BlogComment): Int {
        val qBlogComment = QBlogComment.blogComment

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.blogId != null) {
            cols.add(qBlogComment.blogId)
            values.add(record.blogId)
        }

        if(record.commentator != null) {
            cols.add(qBlogComment.commentator)
            values.add(record.commentator)
        }

        if(record.email != null) {
            cols.add(qBlogComment.email)
            values.add(record.email)
        }

        if(record.websiteUrl != null) {
            cols.add(qBlogComment.websiteUrl)
            values.add(record.websiteUrl)
        }

        if(record.commentBody != null) {
            cols.add(qBlogComment.commentBody)
            values.add(record.commentBody)
        }

        if(record.commentCreateTime != null) {
            cols.add(qBlogComment.commentCreateTime)
            values.add(record.commentCreateTime)
        }

        if(record.commentatorIp != null) {
            cols.add(qBlogComment.commentatorIp)
            values.add(record.commentatorIp)
        }

        if(record.replyBody != null) {
            cols.add(qBlogComment.replyBody)
            values.add(record.replyBody)
        }

        if(record.replyCreateTime != null) {
            cols.add(qBlogComment.replyCreateTime)
            values.add(record.replyCreateTime)
        }

        if(record.commentStatus != null) {
            cols.add(qBlogComment.commentStatus)
            values.add(record.commentStatus)
        }

        if(record.isDeleted != null) {
            cols.add(qBlogComment.isDeleted)
            values.add(record.isDeleted)
        }

        return queryFactory.update(qBlogComment)
                .set(cols, values)
                .where(qBlogComment.commentId.eq(record.commentId))
                .execute()
                .toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: BlogComment): Int {
        val qBlogComment = QBlogComment.blogComment

        return queryFactory.update(qBlogComment)
                .set(qBlogComment.blogId, record.blogId)
                .set(qBlogComment.commentator, record.commentator)
                .set(qBlogComment.email, record.email)
                .set(qBlogComment.websiteUrl, record.websiteUrl)
                .set(qBlogComment.commentBody, record.commentBody)
                .set(qBlogComment.commentCreateTime, record.commentCreateTime)
                .set(qBlogComment.commentatorIp, record.commentatorIp)
                .set(qBlogComment.replyBody, record.replyBody)
                .set(qBlogComment.replyCreateTime, record.replyCreateTime)
                .set(qBlogComment.commentStatus, record.commentStatus)
                .set(qBlogComment.isDeleted, record.isDeleted)
                .where(qBlogComment.commentId.eq(record.commentId))
                .execute()
                .toInt()
    }

    fun findBlogCommentList(map: Map<*, *>): List<BlogComment> {
        //todo test
        val qBlogComment = QBlogComment.blogComment

        val start = map["start"] as Int
        val limit = map["limit"] as Int

        var predicate: BooleanExpression? = null
        if (map != null && map["blogId"] != null) {
            val blogId = map["blogId"] as Long
            predicate = qBlogComment.blogId.eq(blogId)
        }

        var predicate1: BooleanExpression? = null
        if(map != null && map["commentStatus"] != null) {
            val commentStatus = map["commentStatus"] as Int
            predicate1 = qBlogComment.commentStatus.eq(commentStatus.toByte())
        }
        return if(start != null && limit != null) {
            queryFactory.selectFrom(qBlogComment)
                    .where(predicate)
                    .where(predicate1)
                    .where(qBlogComment.isDeleted.eq(0))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogComment.commentId))
                    .offset(start.toLong())
                    .limit(limit.toLong())
                    .fetchResults()
                    .results
        }else {
            listOf()
        }
    }

    fun getTotalBlogComments(map: Map<*, *>?): Int {
        val qBlogComment = QBlogComment.blogComment

        var predicate: BooleanExpression? = null
        if (map != null && map["blogId"] != null) {
            predicate = qBlogComment.blogId.eq(map["blogId"] as Long)
        }

        var predicate1: BooleanExpression? = null
        if(map != null && map["commentStatus"] != null) {
            predicate1 = qBlogComment.commentStatus.eq((map["commentStatus"] as Int).toByte())
        }

        return queryFactory.selectFrom(qBlogComment)
                .where(predicate)
                .where(predicate1)
                .where(qBlogComment.isDeleted.eq(0))
                .fetchCount()
                .toInt()
    }

    @Transactional
    fun checkDone(ids: List<Long>): Int {
        val qBlogComment = QBlogComment.blogComment

        return queryFactory.update(qBlogComment)
                .set(qBlogComment.commentStatus, 1)
                .where(qBlogComment.commentId.`in`(ids).and(qBlogComment.commentStatus.eq(0)))
                .execute()
                .toInt()
    }

    @Transactional
    fun deleteBatch(ids: List<Long>): Int {
        val qBlogComment = QBlogComment.blogComment

        return queryFactory.update(qBlogComment)
                .set(qBlogComment.isDeleted, 1)
                .where(qBlogComment.commentId.`in`(ids))
                .execute()
                .toInt()
    }
}