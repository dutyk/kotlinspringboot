package io.kang.blog.controller.common

import com.google.code.kaptcha.impl.DefaultKaptcha
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class CommonController {

    @Autowired
    lateinit var captchaProducer: DefaultKaptcha

    @GetMapping("/common/kaptcha")
    @Throws(Exception::class)
    fun defaultKaptcha(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse) {
        var captchaOutputStream: ByteArray? = null
        val imgOutputStream = ByteArrayOutputStream()
        try {
            //生产验证码字符串并保存到session中
            val verifyCode = captchaProducer.createText()
            httpServletRequest.session.setAttribute("verifyCode", verifyCode)
            val challenge = captchaProducer.createImage(verifyCode)
            ImageIO.write(challenge, "jpg", imgOutputStream)
        } catch (e: IllegalArgumentException) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND)
            return
        }

        captchaOutputStream = imgOutputStream.toByteArray()
        httpServletResponse.setHeader("Cache-Control", "no-store")
        httpServletResponse.setHeader("Pragma", "no-cache")
        httpServletResponse.setDateHeader("Expires", 0)
        httpServletResponse.contentType = "image/jpeg"
        val responseOutputStream = httpServletResponse.outputStream
        responseOutputStream.write(captchaOutputStream!!)
        responseOutputStream.flush()
        responseOutputStream.close()
    }
}