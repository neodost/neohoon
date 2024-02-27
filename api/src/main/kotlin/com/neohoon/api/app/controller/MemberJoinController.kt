package com.neohoon.api.app.controller

import com.neohoon.api.app.dto.member.MemberJoinDto
import com.neohoon.api.app.service.member.MemberService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class MemberJoinController(
    private val memberService: MemberService
) {


}