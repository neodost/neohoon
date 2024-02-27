package com.neohoon.auth.app.repository.member

import com.neohoon.domain.entity.member.MemberDefaultLogin
import org.springframework.data.jpa.repository.JpaRepository

interface MemberDefaultLoginRepository : JpaRepository<MemberDefaultLogin, Long> {

    fun findByLoginId(loginId: String): MemberDefaultLogin?

}