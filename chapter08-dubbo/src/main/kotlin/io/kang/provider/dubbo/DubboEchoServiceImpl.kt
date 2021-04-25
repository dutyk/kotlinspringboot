package io.kang.provider.dubbo

import io.kang.provider.service.DubboEchoService
import org.apache.dubbo.config.annotation.Service

@Service
class DubboEchoServiceImpl: DubboEchoService {
    override fun echo(name: String): String {
        return "DubboEchoServiceImpl#echo hi $name"
    }
}