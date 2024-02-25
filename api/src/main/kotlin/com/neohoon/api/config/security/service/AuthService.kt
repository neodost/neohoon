package com.neohoon.api.config.security.service

import com.neohoon.core.security.authentication.AuthenticationService
import com.neohoon.core.security.authentication.token.TokenProvider
import org.springframework.stereotype.Service

@Service
class AuthService(
    tokenProvider: TokenProvider
) : AuthenticationService(tokenProvider) {

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

}