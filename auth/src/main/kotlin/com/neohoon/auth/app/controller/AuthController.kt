package com.neohoon.auth.app.controller

import com.neohoon.auth.config.security.service.AuthService
import com.neohoon.core.exception.client.AuthenticationFailException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/authenticate")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/refresh")
    fun refreshAuth(
        request: HttpServletRequest
    ): ResponseEntity<Unit> {
        val accessToken = obtainAccessToken(request)
        val refreshToken = obtainRefreshToken(request)
        log.debug { "refreshToken in cookie : $refreshToken" }

        return authService.refreshToken(refreshToken, accessToken)?.let {
            log.debug { "token refreshed" }
            ResponseEntity.ok().headers { header ->
                header.add(AuthService.AUTHORIZATION_HEADER_NAME, it.accessToken)
                header.add("Set-Cookie", authService.getRefreshTokenCookie(it.refreshToken).toString())
            }.build()
        } ?: let {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers { header ->
                header.add("Set-Cookie", authService.deleteRefreshTokenCookie(refreshToken).toString())
            }.build()
        }
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<Unit> {
        val refreshToken = obtainRefreshToken(request)
        log.debug { "refreshToken for logout in cookie : $refreshToken" }

        return ResponseEntity.ok().headers { header ->
            header.add("Set-Cookie", authService.deleteRefreshTokenCookie(refreshToken).toString())
        }.build()
    }

    private fun obtainAccessToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AuthService.AUTHORIZATION_HEADER_NAME)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        authenticationFail("access token not present")
    }

    private fun obtainRefreshToken(request: HttpServletRequest): String {
        return (request.cookies ?: arrayOf())
            .filter { it.name == AuthService.REFRESH_TOKEN_COOKIE_NAME }
            .map { it.value }
            .firstOrNull()
            ?: authenticationFail("refresh token not present");
    }

    private fun authenticationFail(message: String): Nothing {
        throw AuthenticationFailException(message)
    }

}