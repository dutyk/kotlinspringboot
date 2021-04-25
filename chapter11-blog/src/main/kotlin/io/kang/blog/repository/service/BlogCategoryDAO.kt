package io.kang.blog.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.BlogCategory
import io.kang.blog.entity.QBlogCategory
import io.kang.blog.util.PageQueryUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogCategoryDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var blogCategoryRepository: BlogCategoryRepository

    @Transactional
    fun deleteByPrimaryKey(categoryId: Int): Int {
        val qBlogCategory = QBlogCategory.blogCategory

        val predicate = qBlogCategory.categoryId.eq(categoryId)
                .and(qBlogCategory.isDeleted.eq(0))

        return queryFactory.update(qBlogCategory)
                .set(qBlogCategory.isDeleted, 1)
                .where(predicate)
                .execute()
                .toInt()
    }

    @Transactional
    fun insert(record: BlogCategory): Int {
        blogCategoryRepository.save(record)
        return 0
    }

    @Transactional
    fun insertSelective(record: BlogCategory): Int {
        blogCategoryRepository.save(record)
        return 0
    }

    fun selectByPrimaryKey(categoryId: Int?): BlogCategory? {
        val qBlogCategory = QBlogCategory.blogCategory

        val predicate = qBlogCategory.categoryId.eq(categoryId)
                .and(qBlogCategory.isDeleted.eq(0))

        return queryFactory.selectFrom(qBlogCategory)
                .where(predicate)
                .fetchOne()
    }

    fun selectByCategoryName(categoryName: String): BlogCategory? {
        val qBlogCategory = QBlogCategory.blogCategory

        val predicate = qBlogCategory.categoryName.eq(categoryName)
                .and(qBlogCategory.isDeleted.eq(0))

        return queryFactory.selectFrom(qBlogCategory)
                .where(predicate)
                .fetchOne()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: BlogCategory): Int {
        val qBlogCategory = QBlogCategory.blogCategory

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.categoryName != null) {
            cols.add(qBlogCategory.categoryName)
            values.add(record.categoryName)
        }

        if(record.categoryIcon != null) {
            cols.add(qBlogCategory.categoryIcon)
            values.add(record.categoryIcon)
        }

        if(record.categoryRank != null) {
            cols.add(qBlogCategory.categoryRank)
            values.add(record.categoryRank)
        }

        if(record.isDeleted != null) {
            cols.add(qBlogCategory.isDeleted)
            values.add(record.isDeleted)
        }

        if(record.createTime != null) {
            cols.add(qBlogCategory.createTime)
            values.add(record.createTime)
        }
        return queryFactory.update(qBlogCategory)
                .set(cols, values)
                .where(qBlogCategory.categoryId.eq(record.categoryId))
                .execute()
                .toInt()
    }

    @Transactional
    fun updateByPrimaryKey(record: BlogCategory): Int {
        val qBlogCategory = QBlogCategory.blogCategory

        return queryFactory.update(qBlogCategory)
                .set(qBlogCategory.categoryName, record.categoryName)
                .set(qBlogCategory.categoryIcon, record.categoryIcon)
                .set(qBlogCategory.categoryRank, record.categoryRank)
                .set(qBlogCategory.isDeleted, record.isDeleted)
                .set(qBlogCategory.createTime, record.createTime)
                .where(qBlogCategory.categoryId.eq(record.categoryId))
                .execute()
                .toInt()
    }

    fun findCategoryList(pageUtil: PageQueryUtil?): List<BlogCategory> {
        val qBlogCategory = QBlogCategory.blogCategory

        if(pageUtil == null) return queryFactory.selectFrom(qBlogCategory)
                .where(qBlogCategory.isDeleted.eq(0))
                .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.categoryRank))
                .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.createTime))
                .fetchResults()
                .results

        val start = pageUtil["start"] as Int
        val limit = pageUtil["limit"] as Int

        return if(start != null && limit != null) {
            queryFactory.selectFrom(qBlogCategory)
                    .where(qBlogCategory.isDeleted.eq(0))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.categoryRank))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.createTime))
                    .offset(start.toLong())
                    .limit(limit.toLong())
                    .fetchResults()
                    .results
        }else {
            queryFactory.selectFrom(qBlogCategory)
                    .where(qBlogCategory.isDeleted.eq(0))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.categoryRank))
                    .orderBy(OrderSpecifier(Order.DESC, qBlogCategory.createTime))
                    .fetchResults()
                    .results
        }
    }

    fun selectByCategoryIds(categoryIds: List<Int?>): List<BlogCategory> {
        val qBlogCategory = QBlogCategory.blogCategory

        val predicate = qBlogCategory.categoryId.`in`(categoryIds)
                .and(qBlogCategory.isDeleted.eq(0))

        return queryFactory.selectFrom(qBlogCategory)
                .where(predicate)
                .fetch()
    }

    fun getTotalCategories(pageUtil: PageQueryUtil?): Int {
        val qBlogCategory = QBlogCategory.blogCategory

        return queryFactory.selectFrom(qBlogCategory)
                .where(qBlogCategory.isDeleted.eq(0))
                .fetchCount()
                .toInt()
    }

    @Transactional
    fun deleteBatch(ids: List<Int>): Int {
        val qBlogCategory = QBlogCategory.blogCategory

        val predicate = qBlogCategory.categoryId.`in`(ids)

        return queryFactory.update(qBlogCategory)
                .set(qBlogCategory.isDeleted, 1)
                .where(predicate)
                .execute()
                .toInt()
    }
}