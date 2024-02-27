package com.neohoon.api.app.repository.member

import com.neohoon.domain.entity.member.MemberAuthority
import org.springframework.data.jpa.repository.JpaRepository

interface MemberAuthorityRepository : JpaRepository<MemberAuthority, Long> {
}