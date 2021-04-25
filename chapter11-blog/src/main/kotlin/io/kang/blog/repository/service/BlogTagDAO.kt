package io.kang.blog.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.*
import io.kang.blog.util.PageQueryUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogTagDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogTagRepository: BlogTagRepository

    @Transactional
    fun deleteByPrimaryKey(tagId: Int): Int {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.update(qBlogTag)
                .set(qBlogTag.isDeleted, 1)
                .where(qBlogTag.tagId.eq(tagId))
                .execute().toInt()
    }

    @Transactional
    fun insert(record: BlogTag): Int {
        blogTagRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: BlogTag): Int {
        blogTagRepository.save(record)
        return 0
    }


    fun selectByPrimaryKey(tagId: Int): BlogTag {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.selectFrom(qBlogTag)
                .where(qBlogTag.tagId.eq(tagId).and(qBlogTag.isDeleted.eq(0)))
                .fetchFirst()
    }


    fun selectByTagName(tagName: String): BlogTag? {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.selectFrom(qBlogTag)
                .where(qBlogTag.tagName.eq(tagName).and(qBlogTag.isDeleted.eq(0)))
                .fetchOne()
    }


    @Transactional
    fun updateByPrimaryKeySelective(record: BlogTag): Int {
        val qBlogTag = QBlogTag.blogTag

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.tagName != null) {
            cols.add(qBlogTag.tagName)
            values.add(record.tagName)
        }

        if(record.isDeleted != null) {
            cols.add(qBlogTag.isDeleted)
            values.add(record.isDeleted)
        }

        if(record.createTime != null) {
            cols.add(qBlogTag.createTime)
            values.add(record.createTime)
        }

        return queryFactory.update(qBlogTag)
                .set(cols, values)
                .where(qBlogTag.tagId.eq(record.tagId))
                .execute()
                .toInt()
    }


    @Transactional
    fun updateByPrimaryKey(record: BlogTag): Int {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.update(qBlogTag)
                .set(qBlogTag.tagName, record.tagName)
                .set(qBlogTag.isDeleted, record.isDeleted)
                .set(qBlogTag.createTime, record.createTime)
                .where(qBlogTag.tagId.eq(record.tagId))
                .execute()
                .toInt()
    }


    fun findTagList(pageUtil: PageQueryUtil): List<BlogTag> {
        val qBlogTag = QBlogTag.blogTag

        val start = pageUtil["start"] as Int
        val limit = pageUtil["limit"] as Int

        return  queryFactory.selectFrom(qBlogTag)
                .where(qBlogTag.isDeleted.eq(0))
                .orderBy(OrderSpecifier(Order.DESC, qBlogTag.tagId))
                .offset(start.toLong())
                .limit(limit.toLong())
                .fetchResults().results
    }

    //todo test
    fun getTagCount(): List<BlogTagCount> {
        val qBlogTag = QBlogTag.blogTag
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation
        val qBlog = QBlog.blog

        val result = queryFactory.select(qBlogTagRelation.tagId, qBlogTag.tagName, qBlogTagRelation.tagId.count().`as`("tagCount"))
                .from(qBlogTagRelation)
                .leftJoin(qBlog)
                .on(qBlogTagRelation.blogId.eq(qBlog.blogId))
                .where(qBlog.isDeleted.eq(0))
                .groupBy(qBlogTagRelation.tagId)
                .orderBy(OrderSpecifier(Order.DESC, qBlogTagRelation.tagId.count()))
                .limit(20)
                .leftJoin(qBlogTag)
                .on(qBlogTagRelation.tagId.eq(qBlogTag.tagId))
                .where(qBlogTag.isDeleted.eq(0))
                .fetchResults().results

        return result.map {
            val blogTagCount = BlogTagCount()
            blogTagCount.tagId = it.get(0, Int::class.java)!!
            blogTagCount.tagName = it.get(1, String::class.java)!!
            blogTagCount.tagCount = it.get(2, Long::class.java)?.toInt()!!
            blogTagCount
        }.toList()
    }


    fun getTotalTags(pageUtil: PageQueryUtil?): Int {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.selectFrom(qBlogTag)
                .fetchCount().toInt()
    }

    @Transactional
    fun deleteBatch(ids: List<Int>): Int {
        val qBlogTag = QBlogTag.blogTag

        return queryFactory.update(qBlogTag)
                .set(qBlogTag.isDeleted, 1)
                .where(qBlogTag.tagId.`in`(ids))
                .execute().toInt()
    }

    @Transactional
    fun batchInsertBlogTag(tagList: List<BlogTag>): Int {
        blogTagRepository.saveAll(tagList)
        return 0
    }

}