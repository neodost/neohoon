package com.neohoon.auth.config.security.authentication

import com.neohoon.auth.app.repository.member.MemberDefaultLoginRepository
import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.config.security.userdetails.CustomUserDetailsService
import com.neohoon.auth.exception.security.PasswordNotMatchesException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val memberDefaultLoginRepository: MemberDefaultLoginRepository
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {

        val login = getUsernameByLoginId(authentication.principal.toString())

        val user = userDetailsService.loadUserByUsername(login.username)

        if (!passwordEncoder.matches(authentication.credentials.toString(), login.password)) {
            throw PasswordNotMatchesException()
        }

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    private fun getUsernameByLoginId(loginId: String) =
        memberDefaultLoginRepository.findByLoginId(loginId)
            ?.takeIf { it -> !it.deleted }
            ?: throw UsernameNotFoundException("$loginId not found")

}