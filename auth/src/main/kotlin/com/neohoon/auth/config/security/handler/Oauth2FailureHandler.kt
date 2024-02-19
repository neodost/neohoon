package com.neohoon.auth.config.security.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

private val log = KotlinLogging.logger {}

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
        log.error(exception) { "authenticationFailed : ${exception?.message}" }
        with (response) {
            //TODO: html page
            sendRedirect(UriComponentsBuilder.fromUriString(frontTarget).toUriString())
        }
    }
}