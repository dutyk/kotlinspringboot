package io.kang.provider.service

interface DubboEchoService {
    fun echo(name: String): String
}