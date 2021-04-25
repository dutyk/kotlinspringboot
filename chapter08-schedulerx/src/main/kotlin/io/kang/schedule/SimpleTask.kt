package io.kang.schedule

import com.alibaba.edas.schedulerx.ProcessResult
import com.alibaba.edas.schedulerx.ScxSimpleJobContext
import com.alibaba.edas.schedulerx.ScxSimpleJobProcessor

class SimpleTask: ScxSimpleJobProcessor {
    override fun process(context: ScxSimpleJobContext?): ProcessResult {
        println("-----------Hello world---------------")
        return ProcessResult(true)
    }
}