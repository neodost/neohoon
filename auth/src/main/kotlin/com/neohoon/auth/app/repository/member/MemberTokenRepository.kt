package com.neohoon.auth.app.repository.member

import com.neohoon.domain.entity.member.MemberToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberTokenRepository: CrudRepository<MemberToken, String> {
}