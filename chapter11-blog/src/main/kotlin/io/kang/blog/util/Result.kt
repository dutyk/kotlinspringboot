package io.kang.blog.util

import java.io.Serializable

class Result<T> : Serializable {
    var resultCode: Int = 0
    var message: String? = null
    var data: T? = null

    constructor() {}

    constructor(resultCode: Int, message: String) {
        this.resultCode = resultCode
        this.message = message
    }

    override fun toString(): String {
        return "Result{resultCode=$resultCode, message='$message', data=$data}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
