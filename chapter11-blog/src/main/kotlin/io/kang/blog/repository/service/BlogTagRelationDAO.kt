package io.kang.blog.repository

import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.BlogTagRelation
import io.kang.blog.entity.QBlogTagRelation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogTagRelationDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogTagRelationRepository: BlogTagRelationRepository

    @Transactional
    fun deleteByPrimaryKey(relationId: Long): Int {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.delete(qBlogTagRelation)
                .where(qBlogTagRelation.relationId.eq(relationId))
                .execute().toInt()
    }

    @Transactional
    fun insert(record: BlogTagRelation): Int {
        blogTagRelationRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: BlogTagRelation): Int {
        blogTagRelationRepository.save(record)
        return 0
    }

    fun selectByPrimaryKey(relationId: Long): BlogTagRelation {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.selectFrom(qBlogTagRelation)
                .where(qBlogTagRelation.relationId.eq(relationId))
                .fetchFirst()
    }

    fun selectByBlogIdAndTagId(blogId: Long, tagId: Int): BlogTagRelation {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.selectFrom(qBlogTagRelation)
                .where(qBlogTagRelation.blogId.eq(blogId).and(qBlogTagRelation.tagId.eq(tagId)))
                .fetchFirst()
    }

    fun selectDistinctTagIds(tagIds: List<Int>): List<Int> {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.selectDistinct(qBlogTagRelation.tagId)
                .from(qBlogTagRelation)
                .where(qBlogTagRelation.tagId.`in`(tagIds))
                .fetchResults()
                .results
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: BlogTagRelation): Int {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.blogId != null) {
            cols.add(qBlogTagRelation.blogId)
            values.add(record.blogId)
        }

        if(record.tagId != null) {
            cols.add(qBlogTagRelation.tagId)
            values.add(record.tagId)
        }

        if(record.createTime != null) {
            cols.add(qBlogTagRelation.createTime)
            values.add(record.createTime)
        }

        return queryFactory.update(qBlogTagRelation)
                .set(cols, values)
                .where(qBlogTagRelation.relationId.eq(record.relationId))
                .execute()
                .toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: BlogTagRelation): Int {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.update(qBlogTagRelation)
                .set(qBlogTagRelation.blogId, record.blogId)
                .set(qBlogTagRelation.tagId, record.tagId)
                .set(qBlogTagRelation.createTime, record.createTime)
                .where(qBlogTagRelation.relationId.eq(record.relationId))
                .execute().toInt()
    }

    @Transactional
    fun batchInsert(blogTagRelationList: List<BlogTagRelation>): Int {
        blogTagRelationRepository.saveAll(blogTagRelationList)
        return 0
    }

    @Transactional
    fun deleteByBlogId(blogId: Long?): Int {
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        return queryFactory.delete(qBlogTagRelation)
                .where(qBlogTagRelation.blogId.eq(blogId))
                .execute().toInt()
    }
}