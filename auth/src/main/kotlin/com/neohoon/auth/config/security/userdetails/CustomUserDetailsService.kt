package com.neohoon.auth.config.security.userdetails

import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.exception.security.MemberNotFoundException
import com.neohoon.domain.entity.member.MemberLogin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CustomUserDetailsService(
    private val memberLoginRepository: MemberLoginRepository
): UserDetailsService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {

        log.debug("loadUserByUsername / username: {}", username)

        return memberLoginRepository.findByIdOrNull(username)
            ?.takeIf { !it.deleted }
            ?.let { createUserInfo(it) }
            ?: throw MemberNotFoundException()
    }

    private fun createUserInfo(login: MemberLogin): UserInfo {

        val authorities = login.member.roles
            .map { SimpleGrantedAuthority("ROLE_${it.role.name}") }
            .toMutableList()

        return UserInfo(
            username = login.username,
            authorities = authorities
        )
    }

}