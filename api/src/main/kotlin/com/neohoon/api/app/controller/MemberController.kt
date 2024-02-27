package com.neohoon.api.app.controller

import com.neohoon.api.app.service.member.MemberService
import com.neohoon.core.security.userdetails.UserInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/member")
@Validated
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/me")
    fun loginUserInfo(@AuthenticationPrincipal user: UserInfo): UserInfo = user

}