package com.neohoon.auth.app.dto.member

import com.neohoon.core.validation.check.Check
import com.neohoon.core.validation.check.CheckValidatable
import com.neohoon.core.validation.check.CheckValidateResult
import com.neohoon.domain.entity.member.MemberName
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.util.StringUtils

@Check
class MemberJoinDto(
    @field:NotBlank
    @field:Email
    val loginId: String?,
    @field:NotBlank
    @field:Size(min = 6)
    val password: String?,
    @field:NotNull
    val name: MemberName?
) : CheckValidatable {

    override fun validate(): CheckValidateResult {
        if (isEmpty()) {
            return CheckValidateResult.failure("")
        }
        if (exceededLength()) {
            return CheckValidateResult.failure("")
        }
        return super.validate()
    }

    private fun isEmpty() = !StringUtils.hasText(name?.firstName) || !StringUtils.hasText(name?.lastName)
    private fun exceededLength() = (name?.fullName?.length ?: 0) > 0
}