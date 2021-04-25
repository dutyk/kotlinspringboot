package io.kang.blog.controller.admin;

import io.kang.blog.service.*
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin")
class AdminController {

    @Resource
    lateinit var adminUserService: AdminUserService
    
    @Resource
    lateinit var blogService: BlogService
    
    @Resource
    lateinit var categoryService: CategoryService
    
    @Resource
    lateinit var linkService: LinkService
    
    @Resource
    lateinit var tagService: TagService
    
    @Resource
    lateinit var commentService: CommentService


    @GetMapping("/login")
    fun login(): String {
        return "admin/login"
    }

    @GetMapping("/test")
    fun test(): String {
        return "admin/test"
    }


    @GetMapping("", "/", "/index", "/index.html")
    fun index(request: HttpServletRequest): String {
        request.setAttribute("path", "index")
        request.setAttribute("categoryCount", categoryService.getTotalCategories())
        request.setAttribute("blogCount", blogService.getTotalBlogs())
        request.setAttribute("linkCount", linkService.getTotalLinks())
        request.setAttribute("tagCount", tagService.getTotalTags())
        request.setAttribute("commentCount", commentService.getTotalComments())
        request.setAttribute("path", "index")
        return "admin/index"
    }

    @PostMapping(value = ["/login"])
    fun login(@RequestParam("userName") userName: String,
              @RequestParam("password") password: String,
              @RequestParam("verifyCode") verifyCode: String,
              session: HttpSession): String {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空")
            return "admin/login"
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空")
            return "admin/login"
        }
        val kaptchaCode = session?.getAttribute("verifyCode").toString() + ""
        if (StringUtils.isEmpty(kaptchaCode) || verifyCode != kaptchaCode) {
            session.setAttribute("errorMsg", "验证码错误")
            return "admin/login"
        }
        val adminUser = adminUserService.login(userName, password)
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.nickName)
            session.setAttribute("loginUserId", adminUser.adminUserId)
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index"
        } else {
            session.setAttribute("errorMsg", "登陆失败")
            return "admin/login"
        }
    }

    @GetMapping("/profile")
    fun profile(request: HttpServletRequest): String {
        val loginUserId = request.session.getAttribute("loginUserId") as Int
        val adminUser = adminUserService.getUserDetailById(loginUserId) ?: return "admin/login"
        request.setAttribute("path", "profile")
        request.setAttribute("loginUserName", adminUser.loginUserName)
        request.setAttribute("nickName", adminUser.nickName)
        return "admin/profile"
    }

    @PostMapping("/profile/password")
    @ResponseBody
    fun passwordUpdate(request: HttpServletRequest, @RequestParam("originalPassword") originalPassword: String,
                       @RequestParam("newPassword") newPassword: String): String {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空"
        }
        val loginUserId = request.session.getAttribute("loginUserId") as Int
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.session.removeAttribute("loginUserId")
            request.session.removeAttribute("loginUser")
            request.session.removeAttribute("errorMsg")
            return "success"
        } else {
            return "修改失败"
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    fun nameUpdate(request: HttpServletRequest, @RequestParam("loginUserName") loginUserName: String,
                   @RequestParam("nickName") nickName: String): String {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空"
        }
        val loginUserId = request.session.getAttribute("loginUserId") as Int
        return if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            "success"
        } else {
            "修改失败"
        }
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        request.session.removeAttribute("loginUserId")
        request.session.removeAttribute("loginUser")
        request.session.removeAttribute("errorMsg")
        return "admin/login"
    }
}

