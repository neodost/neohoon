package com.neohoon.api.app.controller

import com.neohoon.api.config.security.userdetails.UserInfo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/\${neohoon.api.version}")
class TestController {

    @GetMapping("/member/me")
    fun test(@AuthenticationPrincipal user: UserInfo): UserInfo = user

    @GetMapping("/test/ok")
    fun test2() = "OK"

}