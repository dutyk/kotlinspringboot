package io.kang.sentinel

import com.alibaba.csp.sentinel.slots.block.BlockException

class ExceptionUtil {
    fun handleException(ex: BlockException): String {
        return "Oops:${ex.javaClass.canonicalName}"
    }
}