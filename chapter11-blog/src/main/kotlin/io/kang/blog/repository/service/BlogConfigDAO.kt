package io.kang.blog.repository

import com.querydsl.core.types.Path
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kang.blog.entity.BlogConfig
import io.kang.blog.entity.QBlogConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlogConfigDAO {
    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    fun selectAll(): List<BlogConfig> {
        val qBlogConfig = QBlogConfig.blogConfig

        return queryFactory.selectFrom(qBlogConfig)
                .fetchResults()
                .results
    }

    fun selectByPrimaryKey(configName: String): BlogConfig {
        val qBlogConfig = QBlogConfig.blogConfig

        val predicate = qBlogConfig.configName.eq(configName)

        return queryFactory.selectFrom(qBlogConfig)
                .where(predicate)
                .fetchFirst()
    }

    @Transactional
    fun updateByPrimaryKeySelective(record: BlogConfig): Int {
        val qBlogConfig = QBlogConfig.blogConfig

        val cols = arrayListOf<Path<*>>()
        val values = arrayListOf<Any?>()

        if(record.configName != null) {
            cols.add(qBlogConfig.configName)
            values.add(record.configName)
        }

        if(record.createTime != null) {
            cols.add(qBlogConfig.createTime)
            values.add(record.createTime)
        }

        if(record.updateTime != null) {
            cols.add(qBlogConfig.updateTime)
            values.add(record.updateTime)
        }

        return queryFactory.update(qBlogConfig)
                .set(cols, values)
                .where(qBlogConfig.configName.eq(record.configName))
                .execute()
                .toInt()
    }
}