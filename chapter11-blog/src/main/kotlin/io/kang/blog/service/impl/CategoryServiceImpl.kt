package io.kang.blog.service.impl

import io.kang.blog.entity.BlogCategory
import io.kang.blog.repository.BlogCategoryDAO
import io.kang.blog.repository.BlogDAO
import io.kang.blog.service.CategoryService
import io.kang.blog.util.PageResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import io.kang.blog.util.PageQueryUtil as PageQueryUtil1

@Service
class CategoryServiceImpl: CategoryService {
    @Autowired
    lateinit var blogCategoryDAO: BlogCategoryDAO

    @Autowired
    lateinit var blogDAO: BlogDAO

    override fun getBlogCategoryPage(pageUtil: PageQueryUtil1): PageResult {
        val categoryList = blogCategoryDAO.findCategoryList(pageUtil)
        val total = blogCategoryDAO.getTotalCategories(pageUtil)
        return PageResult(categoryList, total, pageUtil.limit, pageUtil.page)
    }

    override fun getTotalCategories(): Int {
        return blogCategoryDAO.getTotalCategories(null)
    }

    override fun saveCategory(categoryName: String, categoryIcon: String): Boolean {
        val temp = blogCategoryDAO.selectByCategoryName(categoryName)
        if (temp == null) {
            val blogCategory = BlogCategory()
            blogCategory.categoryName = categoryName
            blogCategory.categoryIcon = categoryIcon
            return blogCategoryDAO.insertSelective(blogCategory) > 0
        }
        return false
    }

    @Transactional
    override fun updateCategory(categoryId: Int, categoryName: String, categoryIcon: String): Boolean {
        val blogCategory = blogCategoryDAO.selectByPrimaryKey(categoryId)
        if (blogCategory != null) {
            blogCategory.categoryIcon = categoryIcon
            blogCategory.categoryName = categoryName
            //修改分类实体
            blogDAO.updateBlogCategorys(categoryName, blogCategory.categoryId!!, listOf<Long>(categoryId.toLong()))
            return blogCategoryDAO.updateByPrimaryKeySelective(blogCategory) > 0
        }
        return false
    }

    @Transactional
    override fun deleteBatch(ids: List<Int>): Boolean {
        if (ids.size < 1) {
            return false
        }
        //修改tb_blog表
        blogDAO!!.updateBlogCategorys("默认分类", 0, ids as List<Long>)
        //删除分类数据
        return blogCategoryDAO.deleteBatch(ids) > 0
    }

    override fun getAllCategories(): List<BlogCategory> {
        return blogCategoryDAO.findCategoryList(null)
    }

}