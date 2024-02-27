package com.neohoon.auth.app.controller

import com.neohoon.auth.app.dto.member.MemberJoinDto
import com.neohoon.auth.app.service.member.MemberService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/v1/join")
@Validated
class JoinController(
    private val memberService: MemberService
) {

    @PostMapping
    fun join(@RequestBody @Valid join: MemberJoinDto) {
        memberService.join(join.loginId!!, join.password!!, join.name!!)
    }

}