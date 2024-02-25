package com.neohoon.api.app.controller

import com.neohoon.api.app.dto.member.MemberJoinDto
import com.neohoon.core.security.userdetails.UserInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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