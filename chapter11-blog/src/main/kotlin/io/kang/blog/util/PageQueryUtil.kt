package io.kang.blog.util

import java.util.LinkedHashMap

class PageQueryUtil(params: Map<String, Any?>) : LinkedHashMap<String, Any?>() {
    //当前页码
    var page: Int = 0
    //每页条数
    var limit: Int = 0

    init {
        this.putAll(params)
        //分页参数
        this.page = Integer.parseInt(params["page"].toString())
        this.limit = Integer.parseInt(params["limit"].toString())
        this["start"] = (page - 1) * limit
        this["page"] = page
        this["limit"] = limit
    }

    override fun toString(): String {
        return "PageUtil{page=$page, limit=$limit}"
    }
}
