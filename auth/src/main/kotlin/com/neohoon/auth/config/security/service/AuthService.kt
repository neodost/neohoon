package com.neohoon.auth.config.security.service

import com.neohoon.auth.app.domain.RefreshToken
import com.neohoon.auth.app.redisrepository.RefreshTokenRepository
import com.neohoon.auth.config.security.dto.TokenDto
import com.neohoon.auth.config.security.userdetails.CustomUserDetailsService
import com.neohoon.auth.config.security.userdetails.UserInfo
import com.neohoon.core.authentication.token.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProvider: TokenProvider,
    private val userDetailsService: CustomUserDetailsService
) {

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
        const val REFRESH_TOKEN_COOKIE_NAME = "nhrt"
        const val REFRESH_TOKEN_TIME_TO_LIVE: Long = 14 * 86400
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    fun authenticateForOAuth(username: String): TokenDto {
        val user = userDetailsService.loadUserByUsername(username) as UserInfo

        return TokenDto(
            tokenProvider.createToken(
                user.username,
                user.authorities.map { it.authority },
                user.validationKey
            ), generateRefreshToken(user.username, user.validationKey)
        )
    }

    fun generateRefreshToken(username: String, validationKey: String): String {
        val refreshToken = RefreshToken(
            username = username,
            validationKey = validationKey,
            expiration = REFRESH_TOKEN_TIME_TO_LIVE
        ).also { refreshTokenRepository.save(it) }

        log.debug("refreshToken generated")

        return refreshToken.token
    }

    fun refreshToken(token: String, jwt: String): TokenDto? {
        val user = tokenProvider.getUserOfExpiredToken(jwt)
        return refreshTokenRepository.findByIdOrNull(token)
            ?.takeIf { user.username == it.username && user.validationKey == it.validationKey }
            ?.let {
                refreshTokenRepository.delete(it)
                val newValidationKey = UUID.randomUUID().toString()
                TokenDto(
                    tokenProvider.createToken(
                        user.username,
                        user.authorities,
                        newValidationKey
                    ),
                    generateRefreshToken(it.username, newValidationKey)
                )
            }
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

    fun deleteRefreshTokenCookie(refreshToken: String): ResponseCookie {
        refreshTokenRepository.findByIdOrNull(refreshToken)?.let {
            refreshTokenRepository.delete(it)
        }
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME)
            .value(refreshToken)
            .sameSite("None")
            .httpOnly(true)
            .path("/")
            .maxAge(0)
            .secure(true)
            .build()
    }
}