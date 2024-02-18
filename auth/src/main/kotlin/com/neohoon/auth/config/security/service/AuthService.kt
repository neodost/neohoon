package com.neohoon.auth.config.security.service

import com.neohoon.auth.app.repository.member.MemberLoginRepository
import com.neohoon.auth.app.repository.member.MemberTokenRepository
import com.neohoon.auth.config.security.authentication.CustomAuthenticationProvider
import com.neohoon.auth.config.security.dto.TokenDto
import com.neohoon.auth.config.security.userdetails.CustomUserDetailsService
import com.neohoon.auth.config.security.userdetails.UserInfo
import com.neohoon.auth.exception.security.MemberNotFoundException
import com.neohoon.core.authentication.token.TokenProvider
import com.neohoon.domain.entity.member.MemberToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.LocalDateTime

@Service
class AuthService(
    private val authenticationProvider: CustomAuthenticationProvider,
    private val memberLoginRepository: MemberLoginRepository,
    private val memberTokenRepository: MemberTokenRepository,
    private val tokenProvider: TokenProvider,
    private val userDetailsService: CustomUserDetailsService,
    @Value("\${neohoon.security.auth.expire-day}")
    private val expireDay: Int
) {

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
        const val REFRESH_TOKEN_COOKIE_NAME = "nhrt"
        const val REFRESH_TOKEN_TIME_TO_LIVE: Long = 14 * 86400
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun authenticateForOAuth(username: String): TokenDto {
        val user = userDetailsService.loadUserByUsername(username) as UserInfo

        UsernamePasswordAuthenticationToken(user, null, user.authorities).also {
            setAuthentication(it)
        }

        return TokenDto(
            tokenProvider.createToken(
                user.username,
                user.authorities.map { it.authority },
                user.validationKey
            ), generateRefreshToken(user.username, user.validationKey)
        )
    }

    @Transactional
    fun generateRefreshToken(username: String, validationKey: String): String {
        val memberToken = MemberToken(memberLoginRepository.getReferenceById(username), validationKey, expireDay).also {
            memberTokenRepository.save(it)
        }

        log.debug("refreshToken generated")

        return memberToken.token
    }

    @Transactional
    fun refreshToken(token: String, jwt: String): TokenDto? {
        val user = tokenProvider.getUserOfExpiredToken(jwt)
        return memberTokenRepository.findByIdOrNull(token)
            ?.takeIf { user.username == it.login.username && user.validationKey == it.validationKey && tokenNotExpired(it.expireDate) }
            ?.let {
                try {
                    val authentication = authenticationProvider.getAuthenticationByUsername(user.username)
                    memberTokenRepository.delete(it)
                    setAuthentication(authentication)
                    val freshUser = authentication.principal as UserInfo
                    TokenDto(
                        tokenProvider.createToken(
                            freshUser.username,
                            freshUser.authorities.map { it.authority },
                            freshUser.validationKey
                        ),
                        generateRefreshToken(it.login.username, freshUser.validationKey)
                    )
                } catch (e: RuntimeException) {
                    log.debug("refresh token is valid but member not exists")
                    throw MemberNotFoundException()
                }
            }
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

    fun getRefreshTokenCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME)
            .value(refreshToken)
            .sameSite("None")
            .httpOnly(true)
            .path("/")
            .maxAge(REFRESH_TOKEN_TIME_TO_LIVE)
            .secure(true)
            .build()
    }

    fun tokenNotExpired(tokenExpireDate: LocalDateTime): Boolean =
        LocalDateTime.now().isBefore(tokenExpireDate)
}