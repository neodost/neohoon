package com.neohoon.api.config.security.service

import com.neohoon.api.config.security.userdetails.UserInfo
import com.neohoon.core.authentication.token.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class AuthService(
    private val tokenProvider: TokenProvider
) {

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    fun setAuthentication(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun setAuthentication(jwt: String) {
        val tokenUser = tokenProvider.getAuthentication(jwt)
        val authorities = tokenUser.authorities
            .filter { StringUtils.hasText(it) }
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
        val principal = UserInfo(
            username = tokenUser.username,
            authorities = authorities
        )

        val authentication = UsernamePasswordAuthenticationToken(principal, jwt, authorities)
        setAuthentication(authentication)
    }
}