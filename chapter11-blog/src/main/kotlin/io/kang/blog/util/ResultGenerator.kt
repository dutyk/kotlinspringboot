package io.kang.blog.util

import org.springframework.util.StringUtils

object ResultGenerator {
    private const val DEFAULT_SUCCESS_MESSAGE = "SUCCESS"
    private const val DEFAULT_FAIL_MESSAGE = "FAIL"
    private const val RESULT_CODE_SUCCESS = 200
    private const val RESULT_CODE_SERVER_ERROR = 500

    fun genSuccessResult(): Result<Any> {
        val result = Result<Any>()
        result.resultCode = RESULT_CODE_SUCCESS
        result.message = DEFAULT_SUCCESS_MESSAGE
        return result
    }

    fun genSuccessResult(message: String): Result<Any> {
        val result = Result<Any>()
        result.resultCode = RESULT_CODE_SUCCESS
        result.message = message
        return result
    }

    fun genSuccessResult(data: Any): Result<Any> {
        val result = Result<Any>()
        result.resultCode = RESULT_CODE_SUCCESS
        result.message = DEFAULT_SUCCESS_MESSAGE
        result.data = data
        return result
    }

    fun genFailResult(message: String): Result<Any> {
        val result = Result<Any>()
        result.resultCode = RESULT_CODE_SERVER_ERROR
        if (StringUtils.isEmpty(message)) {
            result.message = DEFAULT_FAIL_MESSAGE
        } else {
            result.message = message
        }
        return result
    }

    fun genErrorResult(code: Int, message: String): Result<Any> {
        val result = Result<Any>()
        result.resultCode = code
        result.message = message
        return result
    }
}
