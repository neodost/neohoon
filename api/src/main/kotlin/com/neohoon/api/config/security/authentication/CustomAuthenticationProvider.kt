package com.neohoon.api.config.security.authentication

import com.neohoon.api.exception.security.PasswordNotMatchesException
import com.neohoon.api.config.security.userdetails.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    val userDetailsService: CustomUserDetailsService,
    val passwordEncoder: PasswordEncoder
): AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {

        val user = userDetailsService.loadUserByUsername(authentication.principal.toString())

        if (!passwordEncoder.matches(authentication.credentials.toString(), user.password)) {
            throw PasswordNotMatchesException()
        }

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    fun getAuthenticationByUsername(memberId: Long): Authentication {
        val user = userDetailsService.loadUserByUsername(memberId.toString())
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

}