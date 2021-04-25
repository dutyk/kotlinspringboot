package io.kang.blog.controller.admin

import io.kang.blog.service.CategoryService
import io.kang.blog.util.PageQueryUtil
import io.kang.blog.util.ResultGenerator
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import io.kang.blog.util.Result

@Controller
@RequestMapping("/admin")
class CategoryController {

    @Resource
    lateinit var categoryService: CategoryService

    @GetMapping("/categories")
    fun categoryPage(request: HttpServletRequest): String {
        request.setAttribute("path", "categories")
        return "admin/category"
    }

    /**
     * 分类列表
     */
    @RequestMapping(value = ["/categories/list"], method = [RequestMethod.GET])
    @ResponseBody
    fun list(@RequestParam params: Map<String, Any>): Result<Any> {
        if (StringUtils.isEmpty(params["page"]) || StringUtils.isEmpty(params["limit"])) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val pageUtil = PageQueryUtil(params)
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(pageUtil))
    }

    /**
     * 分类添加
     */
    @RequestMapping(value = ["/categories/save"], method = [RequestMethod.POST])
    @ResponseBody
    fun save(@RequestParam("categoryName") categoryName: String,
             @RequestParam("categoryIcon") categoryIcon: String): Result<Any> {
        if (StringUtils.isEmpty(categoryName)) {
            return ResultGenerator.genFailResult("请输入分类名称！")
        }
        if (StringUtils.isEmpty(categoryIcon)) {
            return ResultGenerator.genFailResult("请选择分类图标！")
        }
        return if (categoryService!!.saveCategory(categoryName, categoryIcon)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("分类名称重复")
        }
    }


    /**
     * 分类修改
     */
    @RequestMapping(value = ["/categories/update"], method = [RequestMethod.POST])
    @ResponseBody
    fun update(@RequestParam("categoryId") categoryId: Int,
               @RequestParam("categoryName") categoryName: String,
               @RequestParam("categoryIcon") categoryIcon: String): Result<Any> {
        if (StringUtils.isEmpty(categoryName)) {
            return ResultGenerator.genFailResult("请输入分类名称！")
        }
        if (StringUtils.isEmpty(categoryIcon)) {
            return ResultGenerator.genFailResult("请选择分类图标！")
        }
        return if (categoryService!!.updateCategory(categoryId, categoryName, categoryIcon)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("分类名称重复")
        }
    }


    /**
     * 分类删除
     */
    @RequestMapping(value = ["/categories/delete"], method = [RequestMethod.POST])
    @ResponseBody
    fun delete(@RequestBody ids: List<Int>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (categoryService!!.deleteBatch(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("删除失败")
        }
    }

}
