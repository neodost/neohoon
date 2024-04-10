package com.neohoon.auth.app.task

import com.neohoon.auth.app.repository.member.MemberAuthorityRepository
import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.app.repository.member.MemberRepository
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberAuthority
import com.neohoon.domain.entity.member.MemberDefaultLogin
import com.neohoon.domain.entity.member.MemberName
import com.neohoon.domain.enums.member.Authority
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DefaultAdminTask(
    private val memberRepository: MemberRepository,
    private val memberLoginRepository: MemberLoginRepository,
    private val memberAuthorityRepository: MemberAuthorityRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val adminCount = memberRepository.countByAuthority(Authority.ADMINISTRATOR)
        if (adminCount == 0L) {
            val member = Member("admin@admin.com", MemberName("admin", "admin"))
            val memberAuthority = MemberAuthority(member, Authority.ADMINISTRATOR)
            val memberLogin = MemberDefaultLogin(member, "admin", passwordEncoder.encode("admin"))

            memberRepository.save(member)
            memberAuthorityRepository.save(memberAuthority)
            memberLoginRepository.save(memberLogin)
        }
    }
}