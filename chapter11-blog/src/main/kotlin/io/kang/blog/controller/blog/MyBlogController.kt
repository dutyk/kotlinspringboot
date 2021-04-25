package io.kang.blog.controller.blog

import io.kang.blog.entity.BlogComment
import io.kang.blog.service.*
import io.kang.blog.util.MyBlogUtils
import io.kang.blog.util.PatternUtil
import io.kang.blog.util.ResultGenerator
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import io.kang.blog.util.Result

@Controller
class MyBlogController {
    @Resource
    lateinit var blogService: BlogService

    @Resource
    lateinit var tagService: TagService

    @Resource
    lateinit var linkService: LinkService

    @Resource
    lateinit var commentService: CommentService

    @Resource
    lateinit var configService: ConfigService

    @Resource
    lateinit var categoryService: CategoryService

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/", "/index", "index.html")
    fun index(request: HttpServletRequest): String {
        return this.page(request, 1)
    }

    /**
     * 首页 分页数据
     *
     * @return
     */
    @GetMapping("/page/{pageNum}")
    fun page(request: HttpServletRequest, @PathVariable("pageNum") pageNum: Int): String {
        val blogPageResult = blogService.getBlogsForIndexPage(pageNum) ?: return "error/error_404"
        request.setAttribute("blogPageResult", blogPageResult)
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1))
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0))
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex())
        request.setAttribute("pageName", "首页")
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/index"
    }

    /**
     * Categories页面(包括分类数据和标签数据)
     *
     * @return
     */
    @GetMapping("/categories")
    fun categories(request: HttpServletRequest): String {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex())
        request.setAttribute("categories", categoryService.getAllCategories())
        request.setAttribute("pageName", "分类页面")
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/category"
    }

    /**
     * 详情页
     *
     * @return
     */
    @GetMapping("/blog/{blogId}", "/article/{blogId}")
    fun detail(request: HttpServletRequest, @PathVariable("blogId") blogId: Long, @RequestParam(value = "commentPage", required = false, defaultValue = "1") commentPage: Int): String {
        val blogDetailVO = blogService.getBlogDetail(blogId)
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO)
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage))
        }
        request.setAttribute("pageName", "详情")
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/detail"
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping("/tag/{tagName}")
    fun tag(request: HttpServletRequest, @PathVariable("tagName") tagName: String): String {
        return tag(request, tagName, 1)
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping("/tag/{tagName}/{page}")
    fun tag(request: HttpServletRequest, @PathVariable("tagName") tagName: String, @PathVariable("page") page: Int): String {
        val blogPageResult = blogService.getBlogsPageByTag(tagName, page)
        request.setAttribute("blogPageResult", blogPageResult)
        request.setAttribute("pageName", "标签")
        request.setAttribute("pageUrl", "tag")
        request.setAttribute("keyword", tagName)
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1))
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0))
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex())
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/list"
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping("/category/{categoryName}")
    fun category(request: HttpServletRequest, @PathVariable("categoryName") categoryName: String): String {
        return category(request, categoryName, 1)
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping("/category/{categoryName}/{page}")
    fun category(request: HttpServletRequest, @PathVariable("categoryName") categoryName: String, @PathVariable("page") page: Int): String {
        val blogPageResult = blogService.getBlogsPageByCategory(categoryName, page)
        request.setAttribute("blogPageResult", blogPageResult)
        request.setAttribute("pageName", "分类")
        request.setAttribute("pageUrl", "category")
        request.setAttribute("keyword", categoryName)
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1))
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0))
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex())
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/list"
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping("/search/{keyword}")
    fun search(request: HttpServletRequest, @PathVariable("keyword") keyword: String): String {
        return search(request, keyword, 1)
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping("/search/{keyword}/{page}")
    fun search(request: HttpServletRequest, @PathVariable("keyword") keyword: String, @PathVariable("page") page: Int): String {
        val blogPageResult = blogService.getBlogsPageBySearch(keyword, page)
        request.setAttribute("blogPageResult", blogPageResult)
        request.setAttribute("pageName", "搜索")
        request.setAttribute("pageUrl", "search")
        request.setAttribute("keyword", keyword)
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1))
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0))
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex())
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/list"
    }


    /**
     * 友情链接页
     *
     * @return
     */
    @GetMapping("/link")
    fun link(request: HttpServletRequest): String {
        request.setAttribute("pageName", "友情链接")
        val linkMap = linkService.getLinksForLinkPage()
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey(0.toByte())) {
                request.setAttribute("favoriteLinks", linkMap.get(0.toByte()))
            }
            if (linkMap.containsKey(1.toByte())) {
                request.setAttribute("recommendLinks", linkMap.get(1.toByte()))
            }
            if (linkMap.containsKey(2.toByte())) {
                request.setAttribute("personalLinks", linkMap.get(2.toByte()))
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs())
        return "blog/$theme/link"
    }

    /**
     * 评论操作
     */
    @PostMapping(value = ["/blog/comment"])
    @ResponseBody
    fun comment(request: HttpServletRequest, session: HttpSession,
                @RequestParam blogId: Long?, @RequestParam verifyCode: String,
                @RequestParam commentator: String, @RequestParam email: String,
                @RequestParam websiteUrl: String, @RequestParam commentBody: String): Result<Any> {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不能为空")
        }
        val kaptchaCode = session.getAttribute("verifyCode").toString() + ""
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGenerator.genFailResult("非法请求")
        }
        if (verifyCode != kaptchaCode) {
            return ResultGenerator.genFailResult("验证码错误")
        }
        val ref = request.getHeader("Referer")
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.genFailResult("非法请求")
        }
        if (null == blogId || blogId < 0) {
            return ResultGenerator.genFailResult("非法请求")
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼")
        }
        if (StringUtils.isEmpty(email)) {
            return ResultGenerator.genFailResult("请输入邮箱地址")
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultGenerator.genFailResult("请输入正确的邮箱地址")
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容")
        }
        if (commentBody.trim { it <= ' ' }.length > 200) {
            return ResultGenerator.genFailResult("评论内容过长")
        }
        val comment = BlogComment()
        comment.blogId = blogId
        comment.commentator = MyBlogUtils.cleanString(commentator)
        comment.email = email
        if (PatternUtil.isURL(websiteUrl)) {
            comment.websiteUrl = websiteUrl
        }
        comment.commentBody = MyBlogUtils.cleanString(commentBody)
        return ResultGenerator.genSuccessResult(commentService.addComment(comment))
    }

    /**
     * 关于页面 以及其他配置了subUrl的文章页
     *
     * @return
     */
    @GetMapping("/{subUrl}")
    fun detail(request: HttpServletRequest, @PathVariable("subUrl") subUrl: String): String {
        val blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl)
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO)
            request.setAttribute("pageName", subUrl)
            request.setAttribute("configurations", configService.getAllConfigs())
            return "blog/$theme/detail"
        } else {
            return "error/error_400"
        }
    }

    companion object {

        //public static String theme = "default";
        //public static String theme = "yummy-jekyll";
        var theme = "amaze"
    }
}