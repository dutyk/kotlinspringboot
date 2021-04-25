package io.kang.blog.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AdminLoginInterceptor: HandlerInterceptor {
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, o: Any): Boolean {
        val uri = request.requestURI
        return if (uri.startsWith("/admin") && null == request.session.getAttribute("loginUser")) {
            request.session.setAttribute("errorMsg", "请重新登陆")
            response!!.sendRedirect(request.contextPath + "/admin/login")
            false
        } else {
            request.session.removeAttribute("errorMsg")
            true
        }
    }

    @Throws(Exception::class)
    override fun postHandle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, o: Any, modelAndView: ModelAndView?) {
    }

    @Throws(Exception::class)
    override fun afterCompletion(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, o: Any, e: Exception?) {

    }
}