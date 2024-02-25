package com.neohoon.auth.config.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

private val log = KotlinLogging.logger {}

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.debug(authException) { "unauthorized : ${request.requestURI}" }
        with (response) {
            contentType = MediaType.APPLICATION_JSON.toString()
            characterEncoding = StandardCharsets.UTF_8.name()
            status = HttpStatus.UNAUTHORIZED.value()
            objectMapper.writeValue(writer, ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED))
        }
    }

}