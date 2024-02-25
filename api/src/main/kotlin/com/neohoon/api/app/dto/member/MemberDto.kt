package com.neohoon.api.app.dto.member

import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberName
import com.neohoon.domain.enums.member.Authority

class MemberDto(
    member: Member
) {
    val name: MemberName = member.name
    val email: String? = member.email
    val authorities: List<Authority> = member.authorities.filter { !it.deleted }.map { it.authority }
}
