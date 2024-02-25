package com.neohoon.core.security.filter

import com.neohoon.core.security.authentication.AuthenticationService
import com.neohoon.core.security.authentication.token.TokenValidateState.*
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger {}

class JwtFilter(
    private val authService: AuthenticationService,
    private val authorizationHeaderName: String,
    ignoreUriPatterns: List<String> = listOf()
): OncePerRequestFilter() {

    private val ignoreUriPatternsMatchers = ignoreUriPatterns.map { AntPathRequestMatcher.antMatcher(it) }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isIgnoreRequest(request)) {
            log.debug { "request ignored JWT filter" }
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = obtainAccessToken(request)
        if (StringUtils.hasText(accessToken)) {
            when (authService.accessTokenValidState(accessToken)) {
                VALID -> authService.setAuthentication(accessToken)
                EXPIRED -> {}
                INVALID -> {}
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun obtainAccessToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(authorizationHeaderName)

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return ""
    }

    private fun isIgnoreRequest(request: HttpServletRequest): Boolean {
        return ignoreUriPatternsMatchers.any { it.matches(request) }
    }

}