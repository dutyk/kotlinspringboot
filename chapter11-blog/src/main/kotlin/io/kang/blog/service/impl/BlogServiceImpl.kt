package io.kang.blog.service.impl

import io.kang.blog.controller.vo.BlogDetailVO
import io.kang.blog.controller.vo.BlogListVO
import io.kang.blog.controller.vo.SimpleBlogListVO
import io.kang.blog.entity.Blog
import io.kang.blog.entity.BlogCategory
import io.kang.blog.entity.BlogTag
import io.kang.blog.entity.BlogTagRelation
import io.kang.blog.repository.*
import io.kang.blog.service.BlogService
import io.kang.blog.util.MarkDownUtil
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.PageResult
import io.kang.blog.util.PatternUtil
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors

@Service
class BlogServiceImpl: BlogService {
    @Autowired
    lateinit var blogDAO: BlogDAO

    @Autowired
    lateinit var blogCategoryDAO: BlogCategoryDAO

    @Autowired
    lateinit var blogTagDAO: BlogTagDAO

    @Autowired
    lateinit var blogTagRelationDAO: BlogTagRelationDAO

    @Autowired
    lateinit var blogCommentDAO: BlogCommentDAO

    @Transactional
    override fun saveBlog(blog: Blog): String {
        val blogCategory = blogCategoryDAO.selectByPrimaryKey(blog.blogCategoryId)
        if (blogCategory == null) {
            blog.blogCategoryId = 0
            blog.blogCategoryName = "默认分类"
        } else {
            //设置博客分类名称
            blog.blogCategoryName = (blogCategory.categoryName)
            //分类的排序值加1
            blogCategory.categoryRank = blogCategory.categoryRank!! + 1
        }
        //处理标签数据
        val tags = blog.blogTags?.split(",")
        if (tags!!.size > 6) {
            return "标签数量限制为6"
        }
        //保存文章
        if (blogDAO.insertSelective(blog) > 0) {
            //新增的tag对象
            val tagListForInsert = ArrayList<BlogTag>()
            //所有的tag对象，用于建立关系数据
            val allTagsList = ArrayList<BlogTag>()
            for (i in tags.indices) {
                val tag = blogTagDAO.selectByTagName(tags[i])
                if (tag == null) {
                    //不存在就新增
                    val tempTag = BlogTag()
                    tempTag.tagName = tags[i]
                    tagListForInsert.add(tempTag)
                } else {
                    allTagsList.add(tag)
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                blogTagDAO.batchInsertBlogTag(tagListForInsert)
            }
            blogCategoryDAO.updateByPrimaryKeySelective(blogCategory!!)
            val blogTagRelations = ArrayList<BlogTagRelation>()
            //新增关系数据
            allTagsList.addAll(tagListForInsert)
            for (tag in allTagsList) {
                val blogTagRelation = BlogTagRelation()
                blogTagRelation.blogId = blog.blogId
                blogTagRelation.tagId = tag.tagId
                blogTagRelations.add(blogTagRelation)
            }
            if (blogTagRelationDAO.batchInsert(blogTagRelations) > 0) {
                return "success"
            }
        }
        return "保存失败"
    }

    override fun getBlogsPage(pageUtil: PageQueryUtil): PageResult {
        val blogList = blogDAO.findBlogList(pageUtil)
        val total = blogDAO!!.getTotalBlogs(pageUtil)
        return PageResult(blogList, total, pageUtil.limit, pageUtil.page)
    }

    override fun deleteBatch(ids: List<Long>): Boolean {
        return blogDAO.deleteBatch(ids) > 0
    }

    override fun getTotalBlogs(): Int {
        return blogDAO.getTotalBlogs(null)
    }

    override fun getBlogById(blogId: Long): Blog {
        return blogDAO.selectByPrimaryKey(blogId)
    }

    @Transactional
    override fun updateBlog(blog: Blog): String {
        val blogForUpdate = blogDAO.selectByPrimaryKey(blog.blogId)?: return "数据不存在"

        blogForUpdate.blogTitle = blog.blogTitle
        blogForUpdate.blogSubUrl = blog.blogSubUrl
        blogForUpdate.blogContent = blog.blogContent
        blogForUpdate.blogCoverImage = blog.blogCoverImage
        blogForUpdate.blogStatus = blog.blogStatus
        blogForUpdate.enableComment = blog.enableComment

        val blogCategory = blogCategoryDAO.selectByPrimaryKey(blog.blogCategoryId)
        if (blogCategory == null) {
            blogForUpdate.blogCategoryId = 0
            blogForUpdate.blogCategoryName = "默认分类"
        } else {
            //设置博客分类名称
            blogForUpdate.blogCategoryName = blogCategory.categoryName
            blogForUpdate.blogCategoryId = blogCategory.categoryId
            //分类的排序值加1
            blogCategory.categoryRank = blogCategory.categoryRank!! + 1
        }
        //处理标签数据
        val tags = blog.blogTags?.split(",")
        if (tags!!.size > 6) {
            return "标签数量限制为6"
        }
        blogForUpdate.blogTags = blog.blogTags
        //新增的tag对象
        val tagListForInsert = ArrayList<BlogTag>()
        //所有的tag对象，用于建立关系数据
        val allTagsList = ArrayList<BlogTag>()
        for (i in tags.indices) {
            val tag = blogTagDAO.selectByTagName(tags[i])
            if (tag == null) {
                //不存在就新增
                val tempTag = BlogTag()
                tempTag.tagName = tags[i]
                tagListForInsert.add(tempTag)
            } else {
                allTagsList.add(tag)
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            blogTagDAO.batchInsertBlogTag(tagListForInsert)
        }
        val blogTagRelations = ArrayList<BlogTagRelation>()
        //新增关系数据
        allTagsList.addAll(tagListForInsert)
        for (tag in allTagsList) {
            val blogTagRelation = BlogTagRelation()
            blogTagRelation.blogId = blog.blogId
            blogTagRelation.tagId = tag.tagId
            blogTagRelation.createTime = Date()
            blogTagRelations.add(blogTagRelation)
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        blogCategoryDAO.updateByPrimaryKeySelective(blogCategory!!)
        blogTagRelationDAO.deleteByBlogId(blog.blogId)
        blogTagRelationDAO.batchInsert(blogTagRelations)
        return if (blogDAO.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            "success"
        } else "修改失败"
    }

    override fun getBlogsForIndexPage(page: Int): PageResult {
        var params = mutableMapOf<String, Any>()
        params.put("page", page)
        //每页8条
        params["limit"] = 8
        params["blogStatus"] = 1//过滤发布状态下的数据
        val pageUtil = PageQueryUtil(params)
        val blogList = blogDAO.findBlogList(pageUtil)
        val blogListVOS = getBlogListVOsByBlogs(blogList)
        val total = blogDAO.getTotalBlogs(pageUtil)
        return PageResult(blogListVOS, total, pageUtil.limit, pageUtil.page)
    }

    override fun getBlogListForIndexPage(type: Int): List<SimpleBlogListVO> {
        val simpleBlogListVOS = ArrayList<SimpleBlogListVO>()
        val blogs = blogDAO.findBlogListByType(type, 9)
        if (!CollectionUtils.isEmpty(blogs)) {
            for (blog in blogs) {
                val simpleBlogListVO = SimpleBlogListVO()
                BeanUtils.copyProperties(blog, simpleBlogListVO)
                simpleBlogListVOS.add(simpleBlogListVO)
            }
        }
        return simpleBlogListVOS
    }

    override fun getBlogDetail(id: Long): BlogDetailVO? {
        val blog = blogDAO.selectByPrimaryKey(id)
        //不为空且状态为已发布
        val blogDetailVO = getBlogDetailVO(blog)
        return if (blogDetailVO != null) {
            blogDetailVO
        } else null
    }

    override fun getBlogsPageByTag(tagName: String, page: Int): PageResult? {
        if (PatternUtil.validKeyword(tagName)) {
            val tag = blogTagDAO.selectByTagName(tagName)
            if (tag != null && page > 0) {
                val param = mutableMapOf<String, Any?>()
                param["page"] = page
                param["limit"] = 9
                param["tagId"] = tag.tagId
                val pageUtil = PageQueryUtil(param)
                val blogList = blogDAO!!.getBlogsPageByTagId(pageUtil)
                val blogListVOS = getBlogListVOsByBlogs(blogList)
                val total = blogDAO!!.getTotalBlogsByTagId(pageUtil)
                return PageResult(blogListVOS, total, pageUtil.limit, pageUtil.page)
            }
        }
        return null
    }

    override fun getBlogsPageByCategory(categoryName: String, page: Int): PageResult? {
        if (PatternUtil.validKeyword(categoryName)) {
            var blogCategory = blogCategoryDAO.selectByCategoryName(categoryName)
            if ("默认分类" == categoryName && blogCategory == null) {
                blogCategory = BlogCategory()
                blogCategory.categoryId = 0
            }
            if (blogCategory != null && page > 0) {
                val param = mutableMapOf<String, Any?>()
                param["page"] = page
                param["limit"] = 9
                param["blogCategoryId"] = blogCategory.categoryId
                param["blogStatus"] = 1//过滤发布状态下的数据
                val pageUtil = PageQueryUtil(param)
                val blogList = blogDAO.findBlogList(pageUtil)
                val blogListVOS = getBlogListVOsByBlogs(blogList)
                val total = blogDAO.getTotalBlogs(pageUtil)
                return PageResult(blogListVOS, total, pageUtil.limit, pageUtil.page)
            }
        }
        return null
    }

    override fun getBlogsPageBySearch(keyword: String, page: Int): PageResult? {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            val param = mutableMapOf<String, Any?>()
            param["page"] = page
            param["limit"] = 9
            param["keyword"] = keyword
            param["blogStatus"] = 1//过滤发布状态下的数据
            val pageUtil = PageQueryUtil(param)
            val blogList = blogDAO.findBlogList(pageUtil)
            val blogListVOS = getBlogListVOsByBlogs(blogList)
            val total = blogDAO.getTotalBlogs(pageUtil)
            return PageResult(blogListVOS, total, pageUtil.limit, pageUtil.page)
        }
        return null
    }

    override fun getBlogDetailBySubUrl(subUrl: String): BlogDetailVO? {
        val blog = blogDAO.selectBySubUrl(subUrl)
        //不为空且状态为已发布
        val blogDetailVO = getBlogDetailVO(blog)
        return if (blogDetailVO != null) {
            blogDetailVO
        } else null
    }

    /**
     * 方法抽取
     *
     * @param blog
     * @return
     */
    private fun getBlogDetailVO(blog: Blog?): BlogDetailVO? {
        if (blog != null && blog.blogStatus!!.toInt() == 1) {
            //增加浏览量
            blog.blogViews = blog.blogViews!!  + 1
            blogDAO.updateByPrimaryKey(blog)
            val blogDetailVO = BlogDetailVO()
            BeanUtils.copyProperties(blog, blogDetailVO)
            blogDetailVO.blogContent = MarkDownUtil.mdToHtml(blogDetailVO.blogContent)
            var blogCategory = blogCategoryDAO.selectByPrimaryKey(blog.blogCategoryId)
            if (blogCategory == null) {
                blogCategory = BlogCategory()
                blogCategory.categoryId = 0
                blogCategory.categoryName = "默认分类"
                blogCategory.categoryIcon = "/admin/dist/img/category/00.png"
            }
            //分类信息
            blogDetailVO.blogCategoryIcon = blogCategory.categoryIcon
            if (!StringUtils.isEmpty(blog.blogTags)) {
                //标签设置
                val tags = blog.blogTags?.split(",")
                blogDetailVO.blogTags = tags
            }
            //设置评论数
            val params = mutableMapOf<String, Any?>()
            params["blogId"] = blog.blogId
            params["commentStatus"] = 1//过滤审核通过的数据
            blogDetailVO.commentCount = blogCommentDAO.getTotalBlogComments(params)
            return blogDetailVO
        }
        return null
    }

    private fun getBlogListVOsByBlogs(blogList: List<Blog>): List<BlogListVO> {
        val blogListVOS = ArrayList<BlogListVO>()
        if (!CollectionUtils.isEmpty(blogList)) {
            val categoryIds = blogList.map { it.blogCategoryId }.toList()
            var blogCategoryMap: Map<Int?, String?> = mutableMapOf()
            if (!CollectionUtils.isEmpty(categoryIds)) {
                val blogCategories = blogCategoryDAO.selectByCategoryIds(categoryIds)
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.map { it.categoryId to it.categoryIcon }.toMap()
                }
            }
            for (blog in blogList) {
                val blogListVO = BlogListVO()
                BeanUtils.copyProperties(blog, blogListVO)
                if (blogCategoryMap.containsKey(blog.blogCategoryId)) {
                    blogListVO.blogCategoryIcon = blogCategoryMap[blog.blogCategoryId]
                } else {
                    blogListVO.blogCategoryId = 0
                    blogListVO.blogCategoryName = "默认分类"
                    blogListVO.blogCategoryIcon = "/admin/dist/img/category/00.png"
                }
                blogListVOS.add(blogListVO)
            }
        }
        return blogListVOS
    }
}