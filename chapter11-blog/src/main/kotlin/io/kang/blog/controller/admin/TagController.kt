package io.kang.blog.controller.admin

import io.kang.blog.service.TagService
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
class TagController {

    @Resource
    lateinit var tagService: TagService

    @GetMapping("/tags")
    fun tagPage(request: HttpServletRequest): String {
        request.setAttribute("path", "tags")
        return "admin/tag"
    }

    @GetMapping("/tags/list")
    @ResponseBody
    fun list(@RequestParam params: Map<String, Any>): Result<Any> {
        if (StringUtils.isEmpty(params["page"]) || StringUtils.isEmpty(params["limit"])) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val pageUtil = PageQueryUtil(params)
        return ResultGenerator.genSuccessResult(tagService!!.getBlogTagPage(pageUtil))
    }


    @PostMapping("/tags/save")
    @ResponseBody
    fun save(@RequestParam("tagName") tagName: String): Result<Any> {
        if (StringUtils.isEmpty(tagName)) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (tagService.saveTag(tagName)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("标签名称重复")
        }
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    fun delete(@RequestBody ids: List<Int>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (tagService.deleteBatch(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("有关联数据请勿强行删除")
        }
    }


}
