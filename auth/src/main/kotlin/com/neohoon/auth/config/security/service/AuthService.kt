package com.neohoon.auth.config.security.service

import com.neohoon.auth.app.domain.RefreshToken
import com.neohoon.auth.app.redisrepository.RefreshTokenRepository
import com.neohoon.auth.config.security.dto.TokenDto
import com.neohoon.core.security.authentication.AuthenticationService
import com.neohoon.core.security.authentication.token.TokenProvider
import com.neohoon.core.security.userdetails.UserInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

private val log = KotlinLogging.logger {}

@Service
class AuthService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProvider: TokenProvider
) : AuthenticationService(tokenProvider) {

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
        const val REFRESH_TOKEN_COOKIE_NAME = "nhrt"
        const val REFRESH_TOKEN_TIME_TO_LIVE: Long = 14 * 86400
    }

    fun getTokenByAuthentication(authentication: Authentication): TokenDto {
        val user = authentication.principal as UserInfo

        val combinedAuthorities: MutableSet<String> = authentication.authorities.map { it.authority }.toMutableSet()
        combinedAuthorities.addAll(user.authorities.map { it.authority })

        return TokenDto(
            tokenProvider.createToken(
                user.username,
                combinedAuthorities,
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

        log.debug { "refreshToken generated" }

        return refreshToken.token
    }

    fun refreshToken(refreshToken: String, accessToken: String): TokenDto? {
        val user = tokenProvider.getUserByExpiredToken(accessToken)
        return refreshTokenRepository.findByIdOrNull(refreshToken)
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