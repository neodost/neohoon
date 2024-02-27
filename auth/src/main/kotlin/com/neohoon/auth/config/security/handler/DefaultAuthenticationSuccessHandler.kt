package com.neohoon.auth.config.security.handler

import com.neohoon.auth.config.security.service.AuthService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class DefaultAuthenticationSuccessHandler(
    private val authService: AuthService
): SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        authService.getTokenByAuthentication(authentication).also {
            with (response) {
                setHeader(AuthService.AUTHORIZATION_HEADER_NAME, it.accessToken)
                setHeader("Set-Cookie", authService.getRefreshTokenCookie(it.refreshToken).toString())
            }
        }
    }

}