package com.neohoon.auth.config.security

import com.neohoon.auth.config.security.handler.*
import com.neohoon.auth.config.security.service.AuthService
import com.neohoon.core.security.filter.JwtFilter
import com.neohoon.domain.enums.member.Authority
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val authService: AuthService,
    private val userDetailsService: UserDetailsService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val oauth2SuccessHandler: Oauth2SuccessHandler,
    private val oauth2FailureHandler: Oauth2FailureHandler,
    private val switchUserSuccessHandler: SwitchUserSuccessHandler,
    private val switchUserFailureHandler: SwitchUserFailureHandler,
    @Value("\${neohoon.security.cors.allowed-origins}")
    private val allowedOrigins: Array<String>
) {
    val switchUrl = "/api/v1/authenticate/switch"
    val switchExitUrl = "/api/v1/authenticate/switch/exit"
    val oauth2LoginUrlPattern = "/login/oauth2/code/**"
    val oauth2AuthorizationUrlPattern = "/oauth2/authorization/**"

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        with(http) {
            csrf { it.disable() }
            headers { it.disable() }
            cors { it.configurationSource(corsConfigurationSource()) }
            authorizeHttpRequests {
                it.requestMatchers("/actuator/**").permitAll()
                it.requestMatchers("/api/v1/authenticate/switch").hasAuthority(Authority.ADMINISTRATOR.name)
                it.requestMatchers("/api/v1/authenticate/switch/exit").hasAuthority(Authority.ROLE_PREVIOUS_ADMINISTRATOR.name)
                it.requestMatchers("/api/v1/authenticate/**").permitAll()
                it.requestMatchers(*antMatchers(oauth2LoginUrlPattern)).permitAll()
                it.requestMatchers(*antMatchers(oauth2AuthorizationUrlPattern)).permitAll()
                it.anyRequest().denyAll()
            }
            exceptionHandling {
                it.accessDeniedHandler(accessDeniedHandler)
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            oauth2Login {
                it.successHandler(oauth2SuccessHandler)
                it.failureHandler(oauth2FailureHandler)
            }
            addFilterAfter(switchUserFilter(), AuthorizationFilter::class.java)
            addFilterBefore(jwtFilter(authService), UsernamePasswordAuthenticationFilter::class.java)
            build()
        }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = allowedOrigins.toMutableList()
        config.allowedMethods = mutableListOf(GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD).map { it.name() }
        config.allowedHeaders = mutableListOf(AuthService.AUTHORIZATION_HEADER_NAME)
        config.exposedHeaders = mutableListOf(AuthService.AUTHORIZATION_HEADER_NAME)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun switchUserFilter() = SwitchUserFilter().apply {
        setUserDetailsService(userDetailsService)
        setSwitchUserUrl(switchUrl)
        setExitUserUrl(switchExitUrl)
        setSuccessHandler(switchUserSuccessHandler)
        setFailureHandler(switchUserFailureHandler)
        setSwitchAuthorityRole(Authority.ROLE_PREVIOUS_ADMINISTRATOR.name)
    }

    @Bean
    fun jwtFilter(authService: AuthService): JwtFilter = JwtFilter(
        authService,
        AuthService.AUTHORIZATION_HEADER_NAME,
        listOf(switchExitUrl, oauth2LoginUrlPattern, oauth2AuthorizationUrlPattern)
    )

    private fun antMatchers(vararg patterns: String): Array<RequestMatcher> {
        return patterns.map { AntPathRequestMatcher(it) }.toTypedArray()
    }

}