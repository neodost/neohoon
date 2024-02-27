package com.neohoon.auth.app.service.member

import com.neohoon.auth.app.repository.member.MemberAuthorityRepository
import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.app.repository.member.MemberRepository
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberAuthority
import com.neohoon.domain.entity.member.MemberDefaultLogin
import com.neohoon.domain.entity.member.MemberName
import com.neohoon.domain.enums.member.Authority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberLoginRepository: MemberLoginRepository,
    private val memberAuthorityRepository: MemberAuthorityRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun join(loginId: String, password: String, name: MemberName) {
        val member = Member(loginId, name)
        val memberLogin = MemberDefaultLogin(member, loginId, passwordEncoder.encode(password))
        val memberAuthority = MemberAuthority(member, Authority.USER)

        memberRepository.save(member)
        memberLoginRepository.save(memberLogin)
        memberAuthorityRepository.save(memberAuthority)
    }

}