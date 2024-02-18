package com.neohoon.api.app.repository.member

import com.neohoon.domain.entity.member.MemberLogin
import org.springframework.data.jpa.repository.JpaRepository

interface MemberLoginRepository : JpaRepository<MemberLogin, String> {
}