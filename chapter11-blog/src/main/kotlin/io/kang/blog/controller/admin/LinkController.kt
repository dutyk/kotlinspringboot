package io.kang.blog.controller.admin

import io.kang.blog.entity.BlogLink
import io.kang.blog.service.LinkService
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
class LinkController {

    @Resource
    lateinit var linkService: LinkService

    @GetMapping("/links")
    fun linkPage(request: HttpServletRequest): String {
        request.setAttribute("path", "links")
        return "admin/link"
    }

    @GetMapping("/links/list")
    @ResponseBody
    fun list(@RequestParam params: Map<String, Any>): Result<Any> {
        if (StringUtils.isEmpty(params["page"]) || StringUtils.isEmpty(params["limit"])) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val pageUtil = PageQueryUtil(params)
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageUtil))
    }

    /**
     * 友链添加
     */
    @RequestMapping(value = ["/links/save"], method = [RequestMethod.POST])
    @ResponseBody
    fun save(@RequestParam("linkType") linkType: Int?,
             @RequestParam("linkName") linkName: String,
             @RequestParam("linkUrl") linkUrl: String,
             @RequestParam("linkRank") linkRank: Int?,
             @RequestParam("linkDescription") linkDescription: String): Result<Any> {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val link = BlogLink()
        link.linkType = linkType.toByte()
        link.linkRank = linkRank
        link.linkName = linkName
        link.linkUrl = linkUrl
        link.linkDescription = (linkDescription)
        return ResultGenerator.genSuccessResult(linkService.saveLink(link))
    }

    /**
     * 详情
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    fun info(@PathVariable("id") id: Int?): Result<Any> {
        val link = linkService.selectById(id)
        return ResultGenerator.genSuccessResult(link)
    }

    /**
     * 友链修改
     */
    @RequestMapping(value = ["/links/update"], method = [RequestMethod.POST])
    @ResponseBody
    fun update(@RequestParam("linkId") linkId: Int?,
               @RequestParam("linkType") linkType: Int?,
               @RequestParam("linkName") linkName: String,
               @RequestParam("linkUrl") linkUrl: String,
               @RequestParam("linkRank") linkRank: Int?,
               @RequestParam("linkDescription") linkDescription: String): Result<Any> {
        val tempLink = linkService.selectById(linkId) ?: return ResultGenerator.genFailResult("无数据！")
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        tempLink.linkType = linkType.toByte()
        tempLink.linkRank = linkRank
        tempLink.linkName = linkName
        tempLink.linkUrl = linkUrl
        tempLink.linkDescription = (linkDescription)
        return ResultGenerator.genSuccessResult(linkService.updateLink(tempLink))
    }

    /**
     * 友链删除
     */
    @RequestMapping(value = ["/links/delete"], method = [RequestMethod.POST])
    @ResponseBody
    fun delete(@RequestBody ids: List<Int>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (linkService.deleteBatch(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("删除失败")
        }
    }

}
