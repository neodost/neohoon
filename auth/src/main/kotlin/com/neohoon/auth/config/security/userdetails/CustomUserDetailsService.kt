package com.neohoon.auth.config.security.userdetails

import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.exception.security.MemberNotFoundException
import com.neohoon.domain.entity.member.MemberLogin
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger {}

@Component
class CustomUserDetailsService(
    private val memberLoginRepository: MemberLoginRepository
): UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {

        log.debug { "loadUserByUsername / username: $username" }

        return memberLoginRepository.findWithAuthoritiesByUsername(username)
            ?.takeIf { !it.deleted }
            ?.let { createUserInfo(it) }
            ?: throw MemberNotFoundException()
    }

    private fun createUserInfo(login: MemberLogin): UserInfo {

        val authorities = login.member.authorities
            .map { SimpleGrantedAuthority(it.authority.name) }
            .toMutableList()

        return UserInfo(
            username = login.username,
            authorities = authorities
        )
    }

}