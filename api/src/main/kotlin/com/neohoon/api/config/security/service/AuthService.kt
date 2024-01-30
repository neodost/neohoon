package com.neohoon.api.config.security.service

import com.neohoon.api.exception.security.MemberNotFoundException
import com.neohoon.api.app.repository.member.MemberRepository
import com.neohoon.api.config.security.authentication.CustomAuthenticationProvider
import com.neohoon.api.config.security.authentication.TokenProvider
import com.neohoon.api.config.security.dto.TokenDto
import com.neohoon.api.app.repository.member.MemberTokenRepository
import com.neohoon.api.config.security.userdetails.CustomUserDetailsService
import com.neohoon.api.config.security.userdetails.UserInfo
import com.neohoon.domain.entity.member.MemberToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AuthService(
    private val authenticationProvider: CustomAuthenticationProvider,
    private val memberRepository: MemberRepository,
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
    fun authenticateForOAuth(id: String): TokenDto {
        val user = userDetailsService.loadUserByUsername(id) as UserInfo

        UsernamePasswordAuthenticationToken(user, null, user.authorities).also {
            setAuthentication(it)
        }

        return TokenDto(tokenProvider.createToken(user), generateRefreshToken(user.id, user.key))
    }

    @Transactional
    fun generateRefreshToken(memberId: Long, key: String): String {
        val memberToken = MemberToken(memberRepository.getReferenceById(memberId), key, expireDay).also {
            memberTokenRepository.save(it)
        }

        log.debug("refreshToken generated")

        return memberToken.token
    }

    @Transactional
    fun refreshToken(token: String, jwt: String): TokenDto? {
        val user = tokenProvider.getUserOfExpiredToken(jwt)
        return memberTokenRepository.findByIdOrNull(token)
            ?.takeIf { user.id == it.member.id && user.key == it.validationKey && tokenNotExpired(it.expireDate) }
            ?.let {
                try {
                    val authentication = authenticationProvider.getAuthenticationByUsername(user.id)
                    memberTokenRepository.delete(it)
                    setAuthentication(authentication)
                    val freshUser = authentication.principal as UserInfo
                    TokenDto(
                        tokenProvider.createToken(freshUser),
                        generateRefreshToken(it.member.id, freshUser.key)
                    )
                } catch (e: MemberNotFoundException) {
                    log.debug("refresh token is valid but member not exists")
                    null
                }
            }
    }


    fun setAuthentication(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun setAuthentication(jwt: String) {
        setAuthentication(tokenProvider.getAuthentication(jwt))
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