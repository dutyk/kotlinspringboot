package io.kang.blog.controller.admin

import io.kang.blog.config.Constants
import io.kang.blog.entity.Blog
import io.kang.blog.service.BlogService
import io.kang.blog.service.CategoryService
import io.kang.blog.util.MyBlogUtils
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.ResultGenerator
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import io.kang.blog.util.Result
import org.springframework.beans.factory.annotation.Value
import java.io.File

@Controller
@RequestMapping("/admin")
class BlogController {

    @Resource
    lateinit var blogService: BlogService

    @Resource
    lateinit var categoryService: CategoryService

    @Value("\${upload.dir}")
    var uploadDir: String? = null

    @GetMapping("/blogs/list")
    @ResponseBody
    fun list(@RequestParam params: Map<String, Any>): Result<Any> {
        if (StringUtils.isEmpty(params["page"]) || StringUtils.isEmpty(params["limit"])) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val pageUtil = PageQueryUtil(params)
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(pageUtil))
    }


    @GetMapping("/blogs")
    fun list(request: HttpServletRequest): String {
        request.setAttribute("path", "blogs")
        return "admin/blog"
    }

    @GetMapping("/blogs/edit")
    fun edit(request: HttpServletRequest): String {
        request.setAttribute("path", "edit")
        request.setAttribute("categories", categoryService.getAllCategories())
        return "admin/edit"
    }

    @GetMapping("/blogs/edit/{blogId}")
    fun edit(request: HttpServletRequest, @PathVariable("blogId") blogId: Long): String {
        request.setAttribute("path", "edit")
        val blog = blogService.getBlogById(blogId) ?: return "error/error_400"
        request.setAttribute("blog", blog)
        request.setAttribute("categories", categoryService.getAllCategories())
        return "admin/edit"
    }

    @PostMapping("/blogs/save")
    @ResponseBody
    fun save(@RequestParam("blogTitle") blogTitle: String,
             @RequestParam(name = "blogSubUrl", required = false) blogSubUrl: String,
             @RequestParam("blogCategoryId") blogCategoryId: Int,
             @RequestParam("blogTags") blogTags: String,
             @RequestParam("blogContent") blogContent: String,
             @RequestParam("blogCoverImage") blogCoverImage: String,
             @RequestParam("blogStatus") blogStatus: Byte,
             @RequestParam("enableComment") enableComment: Byte): Result<Any> {
        if (StringUtils.isEmpty(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题")
        }
        if (blogTitle.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("标题过长")
        }
        if (StringUtils.isEmpty(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签")
        }
        if (blogTags.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("标签过长")
        }
        if (blogSubUrl.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("路径过长")
        }
        if (StringUtils.isEmpty(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容")
        }
        if (blogTags.trim { it <= ' ' }.length > 100000) {
            return ResultGenerator.genFailResult("文章内容过长")
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空")
        }
        val blog = Blog()
        blog.blogTitle = blogTitle
        blog.blogSubUrl = blogSubUrl
        blog.blogCategoryId = blogCategoryId
        blog.blogTags = blogTags
        blog.blogContent = blogContent
        blog.blogCoverImage = blogCoverImage
        blog.blogStatus = blogStatus
        blog.enableComment = enableComment
        val saveBlogResult = blogService.saveBlog(blog)
        return if ("success" == saveBlogResult) {
            ResultGenerator.genSuccessResult("添加成功")
        } else {
            ResultGenerator.genFailResult(saveBlogResult)
        }
    }

    @PostMapping("/blogs/update")
    @ResponseBody
    fun update(@RequestParam("blogId") blogId: Long,
               @RequestParam("blogTitle") blogTitle: String,
               @RequestParam(name = "blogSubUrl", required = false) blogSubUrl: String,
               @RequestParam("blogCategoryId") blogCategoryId: Int,
               @RequestParam("blogTags") blogTags: String,
               @RequestParam("blogContent") blogContent: String,
               @RequestParam("blogCoverImage") blogCoverImage: String,
               @RequestParam("blogStatus") blogStatus: Byte,
               @RequestParam("enableComment") enableComment: Byte): Result<Any> {
        if (StringUtils.isEmpty(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题")
        }
        if (blogTitle.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("标题过长")
        }
        if (StringUtils.isEmpty(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签")
        }
        if (blogTags.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("标签过长")
        }
        if (blogSubUrl.trim { it <= ' ' }.length > 150) {
            return ResultGenerator.genFailResult("路径过长")
        }
        if (StringUtils.isEmpty(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容")
        }
        if (blogTags.trim { it <= ' ' }.length > 100000) {
            return ResultGenerator.genFailResult("文章内容过长")
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空")
        }
        val blog = Blog()
        blog.blogId = blogId
        blog.blogTitle = blogTitle
        blog.blogSubUrl = blogSubUrl
        blog.blogCategoryId = blogCategoryId
        blog.blogTags = blogTags
        blog.blogContent = blogContent
        blog.blogCoverImage = blogCoverImage
        blog.blogStatus = blogStatus
        blog.enableComment = enableComment
        val updateBlogResult = blogService.updateBlog(blog)
        return if ("success" == updateBlogResult) {
            ResultGenerator.genSuccessResult("修改成功")
        } else {
            ResultGenerator.genFailResult(updateBlogResult)
        }
    }

    @PostMapping("/blogs/md/uploadfile")
    @Throws(IOException::class, URISyntaxException::class)
    fun uploadFileByEditormd(request: HttpServletRequest,
                             response: HttpServletResponse,
                             @RequestParam(name = "editormd-image-file", required = true)
                             file: MultipartFile) {
        val fileName = file.originalFilename
        val suffixName = fileName?.substring(fileName.lastIndexOf("."))
        //生成文件名称通用方法
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val r = Random()
        val tempName = StringBuilder()
        tempName.append(sdf.format(Date())).append(r.nextInt(100)).append(suffixName)
        val newFileName = tempName.toString()
        //创建文件
        val destFile = File(uploadDir + newFileName)
        val fileUrl = MyBlogUtils.getHost(URI(request.requestURL.toString() + "")).toString() + "/upload/" + newFileName
        val fileDirectory = File(uploadDir)
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw IOException("文件夹创建失败,路径为：$fileDirectory")
                }
            }
            file.transferTo(destFile)
            request.characterEncoding = "utf-8"
            response.setHeader("Content-Type", "text/html")
            response.writer.write("{\"success\": 1, \"message\":\"success\",\"url\":\"$fileUrl\"}")
        } catch (e: UnsupportedEncodingException) {
            response.writer.write("{\"success\":0}")
        } catch (e: IOException) {
            response.writer.write("{\"success\":0}")
        }

    }

    @PostMapping("/blogs/delete")
    @ResponseBody
    fun delete(@RequestBody ids: List<Long>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (blogService.deleteBatch(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("删除失败")
        }
    }

}