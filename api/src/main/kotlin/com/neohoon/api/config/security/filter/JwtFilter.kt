package com.neohoon.api.config.security.filter

import com.neohoon.api.config.security.service.AuthService
import com.neohoon.core.authentication.token.TokenProvider
import com.neohoon.core.authentication.token.TokenValidateState.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val tokenProvider: TokenProvider,
    private val authService: AuthService,
): OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val ignoreUriPatterns = listOf("/api/v1/authenticate").map { AntPathRequestMatcher.antMatcher(it) }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isIgnoreRequest(request)) {
            log.debug("request ignored JWT filter")
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = obtainAccessToken(request)
        if (StringUtils.hasText(accessToken)) {
            when (tokenProvider.accessTokenValidState(accessToken)) {
                VALID -> authService.setAuthentication(accessToken)
                EXPIRED -> {}
                INVALID -> {}
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun obtainAccessToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AuthService.AUTHORIZATION_HEADER_NAME)

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return ""
    }

    private fun isIgnoreRequest(request: HttpServletRequest): Boolean {
        return ignoreUriPatterns.any { it.matches(request) }
    }

}