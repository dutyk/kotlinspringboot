package io.kang.zuul

import com.google.common.util.concurrent.RateLimiter
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class RateLimiterFilter: ZuulFilter() {
    val rateLimiter = RateLimiter.create(2.0)

    override fun filterType(): String {
        return PRE_TYPE
    }

    override fun filterOrder(): Int {
        return 1
    }

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()

        if(!rateLimiter.tryAcquire()) {
            ctx.setSendZuulResponse(false)
            ctx.responseStatusCode = HttpStatus.TOO_MANY_REQUESTS.value()
        }

        return null
    }

    override fun shouldFilter(): Boolean {
        val ctx = RequestContext.getCurrentContext()
        val request = ctx.request

        if("/provide/limit" == request.requestURI){
            return true
        }
        return false
    }
}