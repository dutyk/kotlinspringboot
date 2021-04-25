package io.kang.blog.config

import io.kang.blog.interceptor.AdminLoginInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MyBlogWebMvcConfigurer: WebMvcConfigurer {
    @Autowired
    lateinit var adminLoginInterceptor: AdminLoginInterceptor

    @Value("\${upload.dir}")
    var uploadDir: String? = null

    override fun addInterceptors(registry: InterceptorRegistry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadDir)
    }

}