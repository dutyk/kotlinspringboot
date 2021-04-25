package io.kang.blog.service.impl

import io.kang.blog.entity.BlogConfig
import io.kang.blog.repository.BlogConfigDAO
import io.kang.blog.service.ConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors

@Service
class ConfigServiceImpl: ConfigService {
    @Autowired
    lateinit var blogConfigDAO: BlogConfigDAO

    val websiteName = "personal blog"
    val websiteDescription = "personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建"
    val websiteLogo = "/admin/dist/img/logo2.png"
    val websiteIcon = "/admin/dist/img/favicon.png"

    val yourAvatar = "/admin/dist/img/13.png"
    val yourEmail = "2449207463@qq.com"
    val yourName = "十三"

    val footerAbout = "your personal blog. have fun."
    val footerICP = "浙ICP备 xxxxxx-x号"
    val footerCopyRight = "@2018 十三"
    val footerPoweredBy = "personal blog"
    val footerPoweredByURL = "##"

    override fun updateConfig(configName: String, configValue: String): Int {
        val blogConfig = blogConfigDAO.selectByPrimaryKey(configName)
        if (blogConfig != null) {
            blogConfig.configValue = configValue
            blogConfig.updateTime = Date()
            return blogConfigDAO.updateByPrimaryKeySelective(blogConfig)
        }
        return 0
    }

    override fun getAllConfigs(): Map<String, String> {
        //获取所有的map并封装为map
        val blogConfigs = blogConfigDAO.selectAll()
        return blogConfigs.associateBy({it.configName!!}, {it.configValue!!})
    }
    
    private fun transfer(key: String, value: String): String {
        if ("websiteName" == key && StringUtils.isEmpty(value)) {
            return websiteName
        }
        if ("websiteDescription" == key && StringUtils.isEmpty(value)) {
            return websiteDescription
        }
        if ("websiteLogo" == key && StringUtils.isEmpty(value)) {
            return websiteLogo
        }
        if ("websiteIcon" == key && StringUtils.isEmpty(value)) {
            return websiteIcon
        }
        if ("yourAvatar" == key && StringUtils.isEmpty(value)) {
            return yourAvatar
        }
        if ("yourEmail" == key && StringUtils.isEmpty(value)) {
            return yourEmail
        }
        if ("yourName" == key && StringUtils.isEmpty(value)) {
            return yourName
        }
        if ("footerAbout" == key && StringUtils.isEmpty(value)) {
            return footerAbout
        }
        if ("footerICP" == key && StringUtils.isEmpty(value)) {
            return footerICP
        }
        if ("footerCopyRight" == key && StringUtils.isEmpty(value)) {
            return footerCopyRight
        }
        if ("footerPoweredBy" == key && StringUtils.isEmpty(value)) {
            return footerPoweredBy
        }
        if ("footerPoweredByURL" == key && StringUtils.isEmpty(value)) {
            return footerPoweredByURL
        }

        return ""
    }
}