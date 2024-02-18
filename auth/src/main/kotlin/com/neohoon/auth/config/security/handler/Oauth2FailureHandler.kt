package com.neohoon.auth.config.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class Oauth2FailureHandler(
    @Value("\${neohoon.front.target}")
    private val frontTarget: String
): SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        with (response) {
            response.sendError(HttpStatus.UNAUTHORIZED.value())
            sendRedirect(UriComponentsBuilder.fromUriString(frontTarget).toUriString())
        }
    }
}