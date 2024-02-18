package com.neohoon.api.app.dto.member

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class MemberJoinDto(
    @field:NotBlank
    @field:Email
    val username: String?,
    @field:NotBlank
    @field:Size(min = 6)
    val password: String?
) {
}