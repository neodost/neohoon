package com.neohoon.auth.app.repository.member

import com.neohoon.domain.entity.member.MemberRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRoleRepository: JpaRepository<MemberRole, Long> {
}