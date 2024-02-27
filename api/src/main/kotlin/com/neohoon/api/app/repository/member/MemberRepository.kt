package com.neohoon.api.app.repository.member

import com.neohoon.domain.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}