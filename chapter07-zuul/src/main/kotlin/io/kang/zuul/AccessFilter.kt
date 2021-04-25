package io.kang.zuul

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component

@Component
class AccessFilter: ZuulFilter() {
    override fun shouldFilter(): Boolean {
        return true
    }

    override fun filterType(): String {
        return "pre"
    }

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        val request = ctx.request

        println("进入访问过滤器，访问的url:${request.requestURL}，访问的方法：${request.method}")

        val accessToken = request.getHeader("accessToken")

        if(accessToken == null || accessToken.isEmpty()) {
            ctx.setSendZuulResponse(false)
            ctx.responseStatusCode = 401
            return null
        }
        return null
    }

    override fun filterOrder(): Int {
        return 0
    }
}