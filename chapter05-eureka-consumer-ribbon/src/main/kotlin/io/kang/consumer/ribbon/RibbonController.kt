package io.kang.consumer.ribbon

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RibbonController {
    @Autowired
    lateinit var ribbonService: RibbonService

    @GetMapping("/ribbonProvide")
    fun ribbonProvide(): String? {
        return ribbonService.ribbonProvide()
    }
}