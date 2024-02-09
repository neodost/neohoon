package com.neohoon.api.app.controller

import com.neohoon.api.config.security.userdetails.UserInfo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController {

    @GetMapping("/me")
    fun loginUserInfo(@AuthenticationPrincipal user: UserInfo): UserInfo = user

}