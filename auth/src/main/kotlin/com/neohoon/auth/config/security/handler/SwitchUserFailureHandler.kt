package com.neohoon.auth.config.security.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class SwitchUserFailureHandler: SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        log.error(exception) { "e: $exception" }
        with (response) {
            sendError(HttpStatus.BAD_REQUEST.value(), exception?.localizedMessage)
        }
    }
}