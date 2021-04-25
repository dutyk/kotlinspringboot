package io.kang.blog.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.BlogLink
import io.kang.blog.entity.QBlogLink
import io.kang.blog.util.PageQueryUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogLinkDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogLinkRepository: BlogLinkRepository

    @Transactional
    fun deleteByPrimaryKey(linkId: Int): Int {
        val qBlogLink = QBlogLink.blogLink

        return queryFactory.update(qBlogLink)
                .set(qBlogLink.isDeleted, 1)
                .where(qBlogLink.linkId.eq(linkId).and(qBlogLink.isDeleted.eq(0)))
                .execute()
                .toInt()
    }

    @Transactional
    fun insert(record: BlogLink): Int {
        blogLinkRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: BlogLink): Int {
        blogLinkRepository.save(record)
        return 0
    }

    fun selectByPrimaryKey(linkId: Int?): BlogLink {
        val qBloglink = QBlogLink.blogLink

        return queryFactory.selectFrom(qBloglink)
                .where(qBloglink.linkId.eq(linkId).and(qBloglink.isDeleted.eq(0)))
                .fetchFirst()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: BlogLink): Int {
        val qBlogLink = QBlogLink.blogLink

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.linkType != null) {
            cols.add(qBlogLink.linkType)
            values.add(record.linkType)
        }

        if(record.linkName != null) {
            cols.add(qBlogLink.linkName)
            values.add(record.linkName)
        }

        if(record.linkUrl != null) {
            cols.add(qBlogLink.linkUrl)
            values.add(record.linkUrl)
        }

        if(record.linkDescription != null) {
            cols.add(qBlogLink.linkDescription)
            values.add(record.linkDescription)
        }

        if(record.linkRank != null) {
            cols.add(qBlogLink.linkRank)
            values.add(record.linkRank)
        }

        if(record.isDeleted != null) {
            cols.add(qBlogLink.isDeleted)
            values.add(record.isDeleted)
        }

        if(record.createTime != null) {
            cols.add(qBlogLink.createTime)
            values.add(record.createTime)
        }

        return queryFactory.update(qBlogLink)
                .set(cols, values)
                .where(qBlogLink.linkId.eq(record.linkId))
                .execute()
                .toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: BlogLink): Int {
        val qBlogLink = QBlogLink.blogLink

        return queryFactory.update(qBlogLink)
                .set(qBlogLink.linkType, record.linkType)
                .set(qBlogLink.linkName, record.linkName)
                .set(qBlogLink.linkUrl, record.linkUrl)
                .set(qBlogLink.linkDescription, record.linkDescription)
                .set(qBlogLink.linkRank, record.linkRank)
                .set(qBlogLink.isDeleted, record.isDeleted)
                .set(qBlogLink.createTime, record.createTime)
                .where(qBlogLink.linkId.eq(record.linkId))
                .execute().toInt()
    }

    fun findLinkList(pageUtil: PageQueryUtil?): List<BlogLink> {
        val qBlogLink = QBlogLink.blogLink

        if (pageUtil != null) {
            val start = pageUtil["start"] as Int
            val limit = pageUtil["limit"] as Int

            if(start != null && limit != null) {
                return queryFactory.selectFrom(qBlogLink)
                    .where(qBlogLink.isDeleted.eq(0))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogLink.linkId))
                    .offset(start.toLong())
                    .limit(limit.toLong())
                    .fetchResults()
                    .results
            }
        }

        return return queryFactory.selectFrom(qBlogLink)
                .where(qBlogLink.isDeleted.eq(0))
                .orderBy(OrderSpecifier(Order.DESC, qBlogLink.linkId))
                .fetchResults()
                .results
    }

    fun getTotalLinks(pageUtil: PageQueryUtil?): Int {
        val qBlogLink = QBlogLink.blogLink

        return queryFactory.selectFrom(qBlogLink)
                .where(qBlogLink.isDeleted.eq(0))
                .fetchCount()
                .toInt()
    }

    @Transactional
    fun deleteBatch(ids: List<Int>): Int {
        val qBlogLink = QBlogLink.blogLink

        return queryFactory.update(qBlogLink)
                .set(qBlogLink.isDeleted, 1)
                .where(qBlogLink.linkId.`in`(ids))
                .execute()
                .toInt()
    }
}