package io.kang.blog.repository

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.Blog
import io.kang.blog.entity.QBlog
import io.kang.blog.util.PageQueryUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import com.querydsl.core.types.Order
import io.kang.blog.entity.QBlogTagRelation

@Component
class BlogDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogRepository: BlogRepository

    @Transactional
    fun deleteByPrimaryKey(blogId: Long): Int {
        val qBlog = QBlog.blog

        return queryFactory.update(qBlog)
                .set(qBlog.isDeleted, 1)
                .where(qBlog.isDeleted.eq(0).and(qBlog.blogId.eq(blogId)))
                .execute()
                .toInt()
    }

    @Transactional
    fun insert(record: Blog): Int {
        blogRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: Blog): Int {
        blogRepository.save(record)
        return 0
    }


    fun selectByPrimaryKey(blogId: Long?): Blog {
        val qBlog = QBlog.blog

        return queryFactory.selectFrom(qBlog)
                .where(qBlog.blogId.eq(blogId))
                .fetchFirst()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: Blog): Int {
        val qBlog = QBlog.blog

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.blogTitle != null) {
            cols.add(qBlog.blogTitle)
            values.add(record.blogTitle)
        }

        if(record.blogSubUrl != null) {
            cols.add(qBlog.blogSubUrl)
            values.add(record.blogSubUrl)
        }

        if(record.blogCoverImage != null) {
            cols.add(qBlog.blogCoverImage)
            values.add(record.blogCoverImage)
        }

        if(record.blogContent != null) {
            cols.add(qBlog.blogContent)
            values.add(record.blogContent)
        }

        if(record.blogCategoryId != null) {
            cols.add(qBlog.blogCategoryId)
            values.add(record.blogCategoryId)
        }

        if(record.blogCategoryName != null) {
            cols.add(qBlog.blogCategoryName)
            values.add(record.blogCategoryName)
        }

        if(record.blogCategoryName != null) {
            cols.add(qBlog.blogCategoryName)
            values.add(record.blogCategoryName)
        }

        if(record.blogTags != null) {
            cols.add(qBlog.blogTags)
            values.add(record.blogTags)
        }

        if(record.blogStatus != null) {
            cols.add(qBlog.blogStatus)
            values.add(record.blogStatus)
        }

        if(record.blogViews != null) {
            cols.add(qBlog.blogViews)
            values.add(record.blogViews)
        }

        if(record.enableComment != null) {
            cols.add(qBlog.enableComment)
            values.add(record.enableComment)
        }

        if(record.isDeleted != null) {
            cols.add(qBlog.isDeleted)
            values.add(record.isDeleted)
        }

        if(record.createTime != null) {
            cols.add(qBlog.createTime)
            values.add(record.createTime)
        }

        if(record.updateTime != null) {
            cols.add(qBlog.updateTime)
            values.add(record.updateTime)
        }

        if(record.blogContent != null) {
            cols.add(qBlog.blogContent)
            values.add(record.blogContent)
        }

        return queryFactory.update(qBlog)
                .set(cols, values)
                .where(qBlog.blogId.eq(record.blogId))
                .execute()
                .toInt()
    }


    @Transactional
    fun updateByPrimaryKeyWithBLOBs(record: Blog): Int {
        val qBlog = QBlog.blog

        return queryFactory.update(qBlog)
                .set(qBlog.blogTitle, record.blogTitle)
                .set(qBlog.blogSubUrl, record.blogSubUrl)
                .set(qBlog.blogCoverImage, record.blogCoverImage)
                .set(qBlog.blogCategoryId, record.blogCategoryId)
                .set(qBlog.blogCategoryName, record.blogCategoryName)
                .set(qBlog.blogTags, record.blogTags)
                .set(qBlog.blogStatus, record.blogStatus)
                .set(qBlog.blogViews, record.blogViews)
                .set(qBlog.enableComment, record.enableComment)
                .set(qBlog.isDeleted, record.isDeleted)
                .set(qBlog.createTime, record.createTime)
                .set(qBlog.updateTime, record.updateTime)
                .set(qBlog.blogContent, record.blogContent)
                .where(qBlog.blogId.eq(record.blogId))
                .execute().toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: Blog): Int {
        val qBlog = QBlog.blog

        return queryFactory.update(qBlog)
                .set(qBlog.blogTitle, record.blogTitle)
                .set(qBlog.blogSubUrl, record.blogSubUrl)
                .set(qBlog.blogCoverImage, record.blogCoverImage)
                .set(qBlog.blogCategoryId, record.blogCategoryId)
                .set(qBlog.blogCategoryName, record.blogCategoryName)
                .set(qBlog.blogTags, record.blogTags)
                .set(qBlog.blogStatus, record.blogStatus)
                .set(qBlog.blogViews, record.blogViews)
                .set(qBlog.enableComment, record.enableComment)
                .set(qBlog.isDeleted, record.isDeleted)
                .set(qBlog.createTime, record.createTime)
                .set(qBlog.updateTime, record.updateTime)
                .where(qBlog.blogId.eq(record.blogId))
                .execute().toInt()
    }


    fun findBlogList(pageUtil: PageQueryUtil): List<Blog> {
        val qBlog = QBlog.blog

        val start = pageUtil["start"] as Int
        val limit = pageUtil["limit"] as Int

        var predicate: BooleanExpression? = null
        if(pageUtil != null && pageUtil["keyword"] != null) {
            val keyword = pageUtil["keyword"]
            predicate = qBlog.blogTitle.like("%$keyword%").or(qBlog.blogCategoryName.like("%$keyword%"))
        }

        var predicate1: BooleanExpression? = null
        if(pageUtil != null && pageUtil["blogStatus"] != null) {
            val blogStatus = pageUtil["blogStatus"] as Int
            predicate1 = qBlog.blogStatus.eq(blogStatus.toByte())
        }

        var predicate2: BooleanExpression? = null
        if(pageUtil != null && pageUtil["blogCategoryId"] != null) {
            val blogCategoryId = pageUtil["blogCategoryId"] as Int
            predicate2 = qBlog.blogCategoryId.eq(blogCategoryId)
        }

        return queryFactory.selectFrom(qBlog)
                .where(predicate)
                .where(predicate1)
                .where(predicate2)
                .where(qBlog.isDeleted.eq(0))
                .orderBy(OrderSpecifier(Order.DESC, qBlog.blogId))
                .offset(start.toLong())
                .limit(limit.toLong())
                .fetchResults()
                .results
    }


    fun findBlogListByType(type: Int, limit: Int): List<Blog> {
        //todo test
        val qBlog = QBlog.blog

        var order: OrderSpecifier<Long>? = null
        if(type != null && type == 0) {
            order = OrderSpecifier(Order.DESC, qBlog.blogViews)
        }

        if(type != null && type == 1) {
            order = OrderSpecifier(Order.DESC, qBlog.blogId)

        }

        return queryFactory.selectFrom(qBlog)
                .where(qBlog.isDeleted.eq(0).and(qBlog.blogStatus.eq(1)))
                .orderBy(order)
                .limit(limit.toLong())
                .fetchResults().results
    }


    //todo test
    fun getTotalBlogs(pageUtil: PageQueryUtil?): Int {
        val qBlog = QBlog.blog

        var predicate: BooleanExpression? = null
        if(pageUtil!= null && pageUtil["keyword"] != null) {
            val keyword = pageUtil["keyword"]
            predicate = qBlog.blogTitle.like("%$keyword%").or(qBlog.blogCategoryName.like("%$keyword%"))
        }

        var predicate1: BooleanExpression? = null
        if(pageUtil != null && pageUtil["blogStatus"] != null) {
            val blogStatus = pageUtil["blogStatus"] as Int
            predicate1 = qBlog.blogStatus.eq(blogStatus.toByte())
        }

        var predicate2: BooleanExpression? = null
        if(pageUtil != null && pageUtil["blogCategoryId"] != null) {
            val blogCategoryId = pageUtil["blogCategoryId"] as Int
            predicate2 = qBlog.blogCategoryId.eq(blogCategoryId)
        }

        return queryFactory.selectFrom(qBlog)
                .where(predicate)
                .where(predicate1)
                .where(predicate2)
                .where(qBlog.isDeleted.eq(0))
                .fetchCount().toInt()
    }


    @Transactional
    fun deleteBatch(ids: List<Long>): Int {
        val qBlog = QBlog.blog

        return queryFactory.update(qBlog)
                .set(qBlog.isDeleted, 1)
                .where(qBlog.blogId.`in`(ids))
                .execute()
                .toInt()
    }


    fun getBlogsPageByTagId(pageUtil: PageQueryUtil): List<Blog> {
        val qBlog = QBlog.blog
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        val tagId = pageUtil["tagId"] as Int
        val start = pageUtil["start"] as Int
        val limit = pageUtil["limit"] as Int


        val blogIds = queryFactory.select(qBlogTagRelation.blogId)
                .from(qBlogTagRelation)
                .where(qBlogTagRelation.tagId.eq(tagId))
                .fetchResults()
                .results

        return queryFactory.selectFrom(qBlog)
                .where(qBlog.blogStatus.eq(1).and(qBlog.isDeleted.eq(0)))
                .where(qBlog.blogId.`in`(blogIds))
                .orderBy(OrderSpecifier(Order.DESC, qBlog.blogId))
                .offset(start.toLong())
                .limit(limit.toLong())
                .fetchResults()
                .results
    }


    fun getTotalBlogsByTagId(pageUtil: PageQueryUtil): Int {
        val qBlog = QBlog.blog
        val qBlogTagRelation = QBlogTagRelation.blogTagRelation

        val tagId = pageUtil["tagId"] as Int

        val blogIds = queryFactory.select(qBlogTagRelation.blogId)
                .from(qBlogTagRelation)
                .where(qBlogTagRelation.tagId.eq(tagId))
                .fetchResults()
                .results

        return queryFactory.selectFrom(qBlog)
                .where(qBlog.blogStatus.eq(1).and(qBlog.isDeleted.eq(0)))
                .where(qBlog.blogId.`in`(blogIds))
                .fetchCount().toInt()
    }


    fun selectBySubUrl(subUrl: String): Blog? {
        val qBlog = QBlog.blog

        return queryFactory.selectFrom(qBlog)
                .where(qBlog.blogSubUrl.eq(subUrl).and(qBlog.isDeleted.eq(0)))
                .limit(1)
                .fetchOne()
    }

    @Transactional
    fun updateBlogCategorys(categoryName: String, categoryId: Int, ids: List<Long>): Int {
        val qBlog = QBlog.blog

        return queryFactory.update(qBlog)
                .set(qBlog.blogCategoryId, categoryId)
                .set(qBlog.blogCategoryName, categoryName)
                .where(qBlog.blogId.`in`(ids).and(qBlog.isDeleted.eq(0)))
                .execute().toInt()
    }


}