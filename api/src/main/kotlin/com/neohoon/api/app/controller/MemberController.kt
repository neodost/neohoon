package com.neohoon.api.app.controller

import com.neohoon.api.app.dto.member.MemberJoinDto
import com.neohoon.api.config.security.userdetails.UserInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/member")
class MemberController {

    @GetMapping("/me")
    fun loginUserInfo(@AuthenticationPrincipal user: UserInfo): UserInfo = user

    @PostMapping("/join")
    fun joinMember(@RequestBody member: MemberJoinDto) {
        log.info { "username: ${member.username}, password: ${member.password}" }
    }

}