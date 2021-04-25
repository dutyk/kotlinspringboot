package io.kang.blog.controller.admin

import io.kang.blog.service.ConfigService
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import io.kang.blog.util.Result
import io.kang.blog.util.ResultGenerator

@Controller
@RequestMapping("/admin")
class ConfigurationController {

    @Resource
    lateinit var configService: ConfigService

    @GetMapping("/configurations")
    fun list(request: HttpServletRequest): String {
        request.setAttribute("path", "configurations")
        request.setAttribute("configurations", configService.getAllConfigs())
        return "admin/configuration"
    }

    @PostMapping("/configurations/website")
    @ResponseBody
    fun website(@RequestParam(value = "websiteName", required = false) websiteName: String,
                @RequestParam(value = "websiteDescription", required = false) websiteDescription: String,
                @RequestParam(value = "websiteLogo", required = false) websiteLogo: String,
                @RequestParam(value = "websiteIcon", required = false) websiteIcon: String): Result<Any> {
        var updateResult = 0
        if (!StringUtils.isEmpty(websiteName)) {
            updateResult += configService.updateConfig("websiteName", websiteName)
        }
        if (!StringUtils.isEmpty(websiteDescription)) {
            updateResult += configService.updateConfig("websiteDescription", websiteDescription)
        }
        if (!StringUtils.isEmpty(websiteLogo)) {
            updateResult += configService.updateConfig("websiteLogo", websiteLogo)
        }
        if (!StringUtils.isEmpty(websiteIcon)) {
            updateResult += configService.updateConfig("websiteIcon", websiteIcon)
        }
        return ResultGenerator.genSuccessResult(updateResult > 0)
    }

    @PostMapping("/configurations/userInfo")
    @ResponseBody
    fun userInfo(@RequestParam(value = "yourAvatar", required = false) yourAvatar: String,
                 @RequestParam(value = "yourName", required = false) yourName: String,
                 @RequestParam(value = "yourEmail", required = false) yourEmail: String): Result<Any> {
        var updateResult = 0
        if (!StringUtils.isEmpty(yourAvatar)) {
            updateResult += configService.updateConfig("yourAvatar", yourAvatar)
        }
        if (!StringUtils.isEmpty(yourName)) {
            updateResult += configService.updateConfig("yourName", yourName)
        }
        if (!StringUtils.isEmpty(yourEmail)) {
            updateResult += configService.updateConfig("yourEmail", yourEmail)
        }
        return ResultGenerator.genSuccessResult(updateResult > 0)
    }

    @PostMapping("/configurations/footer")
    @ResponseBody
    fun footer(@RequestParam(value = "footerAbout", required = false) footerAbout: String,
               @RequestParam(value = "footerICP", required = false) footerICP: String,
               @RequestParam(value = "footerCopyRight", required = false) footerCopyRight: String,
               @RequestParam(value = "footerPoweredBy", required = false) footerPoweredBy: String,
               @RequestParam(value = "footerPoweredByURL", required = false) footerPoweredByURL: String): Result<Any> {
        var updateResult = 0
        if (!StringUtils.isEmpty(footerAbout)) {
            updateResult += configService.updateConfig("footerAbout", footerAbout)
        }
        if (!StringUtils.isEmpty(footerICP)) {
            updateResult += configService.updateConfig("footerICP", footerICP)
        }
        if (!StringUtils.isEmpty(footerCopyRight)) {
            updateResult += configService.updateConfig("footerCopyRight", footerCopyRight)
        }
        if (!StringUtils.isEmpty(footerPoweredBy)) {
            updateResult += configService.updateConfig("footerPoweredBy", footerPoweredBy)
        }
        if (!StringUtils.isEmpty(footerPoweredByURL)) {
            updateResult += configService.updateConfig("footerPoweredByURL", footerPoweredByURL)
        }
        return ResultGenerator.genSuccessResult(updateResult > 0)
    }


}
