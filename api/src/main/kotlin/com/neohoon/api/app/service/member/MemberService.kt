package com.neohoon.api.app.service.member

import com.neohoon.api.app.repository.member.MemberAuthorityRepository
import com.neohoon.api.app.repository.member.MemberLoginRepository
import com.neohoon.api.app.repository.member.MemberRepository
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberDefaultLogin
import com.neohoon.domain.entity.member.MemberName
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger {}

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberLoginRepository: MemberLoginRepository,
    private val memberAuthorityRepository: MemberAuthorityRepository,
) {

    @Transactional
    fun join(loginId: String, password: String, name: MemberName) {
        val member = Member(loginId, name)
        val memberLogin = MemberDefaultLogin(member, loginId, password)

        memberRepository.save(member)
        memberLoginRepository.save(memberLogin)
    }

}