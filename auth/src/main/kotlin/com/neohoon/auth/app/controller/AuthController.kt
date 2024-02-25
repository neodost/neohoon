package com.neohoon.auth.app.controller

import com.neohoon.auth.config.security.service.AuthService
import com.neohoon.core.exception.client.AuthenticationFailException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpSession
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/authenticate")
@Validated
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/refresh")
    fun refreshAuth(
        @RequestHeader(AuthService.AUTHORIZATION_HEADER_NAME) @Pattern(regexp = "^Bearer .*$") authorization: String,
        @CookieValue(AuthService.REFRESH_TOKEN_COOKIE_NAME) refreshToken: String
    ): ResponseEntity<Unit> {
        log.debug { "authorization in headers: $authorization" }
        log.debug { "refreshToken in cookie: $refreshToken" }

        return authService.refreshToken(refreshToken, authorization.substring(7))?.let {
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
    fun logout(
        @CookieValue(AuthService.REFRESH_TOKEN_COOKIE_NAME) refreshToken: String,
        session: HttpSession?
    ): ResponseEntity<Unit> {
        log.debug { "refreshToken for logout in cookie : $refreshToken" }
        session?.invalidate()
        return ResponseEntity.ok().headers { header ->
            header.add("Set-Cookie", authService.deleteRefreshTokenCookie(refreshToken).toString())
        }.build()
    }

    private fun authenticationFail(message: String): Nothing {
        throw AuthenticationFailException(message)
    }

}