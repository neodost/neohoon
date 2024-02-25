package com.neohoon.core.security.authentication

import com.neohoon.core.security.authentication.token.TokenProvider
import com.neohoon.core.security.userdetails.UserInfo
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils

abstract class AuthenticationService(
    private val tokenProvider: TokenProvider
) {

    fun setAuthentication(accessToken: String) {
        val tokenUser = tokenProvider.getAuthentication(accessToken)
        val authorities = tokenUser.authorities
            .filter { StringUtils.hasText(it) }
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
        val principal = UserInfo(
            username = tokenUser.username,
            authorities = authorities
        )

        val authentication = UsernamePasswordAuthenticationToken(principal, accessToken, authorities)
        setAuthentication(authentication)
    }

    fun accessTokenValidState(accessToken: String) = tokenProvider.accessTokenValidState(accessToken)

    private fun setAuthentication(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

}