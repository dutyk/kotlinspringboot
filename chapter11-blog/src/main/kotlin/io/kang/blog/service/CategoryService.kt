package io.kang.blog.service

import io.kang.blog.entity.BlogCategory
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult

interface CategoryService {
    /**
     * 查询分类的分页数据
     *
     * @param pageUtil
     * @return
     */
    fun getBlogCategoryPage(pageUtil: PageQueryUtil): PageResult

    fun getTotalCategories(): Int

    /**
     * 添加分类数据
     *
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    fun saveCategory(categoryName: String, categoryIcon: String): Boolean

    fun updateCategory(categoryId: Int, categoryName: String, categoryIcon: String): Boolean

    fun deleteBatch(ids: List<Int>): Boolean

    fun getAllCategories(): List<BlogCategory>
}