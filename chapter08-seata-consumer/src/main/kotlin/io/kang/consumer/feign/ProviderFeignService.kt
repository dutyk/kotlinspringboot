package io.kang.consumer.feign

import io.kang.consumer.domain.TbUser
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(value = "sca-provider")
interface ProviderFeignService {
    @PostMapping("/add/user")
    fun add(@RequestBody user: TbUser)
}