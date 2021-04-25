package io.kang.blog.controller.admin

import io.kang.blog.service.CommentService
import io.kang.blog.util.PageQueryUtil
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import io.kang.blog.util.Result
import io.kang.blog.util.ResultGenerator

@Controller
@RequestMapping("/admin")
class CommentController {

    @Resource
    lateinit var commentService: CommentService

    @GetMapping("/comments/list")
    @ResponseBody
    fun list(@RequestParam params: Map<String, Any>): Result<Any> {
        if (StringUtils.isEmpty(params["page"]) || StringUtils.isEmpty(params["limit"])) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        val pageUtil = PageQueryUtil(params)
        return ResultGenerator.genSuccessResult(commentService.getCommentsPage(pageUtil))
    }

    @PostMapping("/comments/checkDone")
    @ResponseBody
    fun checkDone(@RequestBody ids: List<Long>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (commentService.checkDone(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("审核失败")
        }
    }

    @PostMapping("/comments/reply")
    @ResponseBody
    fun checkDone(@RequestParam("commentId") commentId: Long?,
                  @RequestParam("replyBody") replyBody: String): Result<Any> {
        if (commentId == null || commentId < 1 || StringUtils.isEmpty(replyBody)) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (commentService!!.reply(commentId, replyBody)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("回复失败")
        }
    }

    @PostMapping("/comments/delete")
    @ResponseBody
    fun delete(@RequestBody ids: List<Long>): Result<Any> {
        if (ids.size < 1) {
            return ResultGenerator.genFailResult("参数异常！")
        }
        return if (commentService.deleteBatch(ids)) {
            ResultGenerator.genSuccessResult()
        } else {
            ResultGenerator.genFailResult("刪除失败")
        }
    }

    @GetMapping("/comments")
    fun list(request: HttpServletRequest): String {
        request.setAttribute("path", "comments")
        return "admin/comment"
    }


}
