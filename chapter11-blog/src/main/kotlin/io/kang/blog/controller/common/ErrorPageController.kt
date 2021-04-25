package io.kang.blog.controller.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@Controller
class ErrorPageController : ErrorController {

    @Autowired
    lateinit var errorAttributes: ErrorAttributes

    @RequestMapping(value = [ERROR_PATH], produces = ["text/html"])
    fun errorHtml(request: HttpServletRequest): ModelAndView {
        val status = getStatus(request)
        return if (HttpStatus.BAD_REQUEST == status) {
            ModelAndView("error/error_400")
        } else if (HttpStatus.NOT_FOUND == status) {
            ModelAndView("error/error_404")
        } else {
            ModelAndView("error/error_5xx")
        }
    }

    @RequestMapping(value = [ERROR_PATH])
    @ResponseBody
    fun error(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val body = getErrorAttributes(request, getTraceParameter(request))
        val status = getStatus(request)
        return ResponseEntity(body, status)
    }

    override fun getErrorPath(): String {
        return ERROR_PATH
    }


    private fun getTraceParameter(request: HttpServletRequest): Boolean {
        val parameter = request.getParameter("trace") ?: return false
        return "false" != parameter.toLowerCase()
    }

    protected fun getErrorAttributes(request: HttpServletRequest, includeStackTrace: Boolean): Map<String, Any> {
        val webRequest = ServletWebRequest(request)
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace)
    }

    private fun getStatus(request: HttpServletRequest): HttpStatus {
        val statusCode = request
                .getAttribute("javax.servlet.error.status_code") as Int
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode)
            } catch (ex: Exception) {
            }

        }
        return HttpStatus.INTERNAL_SERVER_ERROR
    }

    companion object {
        const val ERROR_PATH: String = "/error"
    }
}