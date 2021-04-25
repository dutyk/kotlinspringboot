package io.kang.blog.util

import java.io.Serializable
import kotlin.math.ceil

class PageResult(
        var list: List<*>?, //总记录数
        var totalCount: Int, //每页记录数
        var pageSize: Int, //当前页数
        var currPage: Int) : Serializable {
    //总页数
    var totalPage: Int = 0

    init {
        this.totalPage = ceil(totalCount.toDouble() / pageSize).toInt()
    }

}
