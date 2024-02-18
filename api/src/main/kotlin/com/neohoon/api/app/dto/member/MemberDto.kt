package com.neohoon.api.app.dto.member

import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberName
import com.neohoon.domain.enums.member.Role

class MemberDto(
    member: Member
) {
    val name: MemberName = member.name
    val email: String? = member.email
    val authorities: List<Role> = member.roles.filter { !it.deleted }.map { it.role }
}
