package com.neohoon.auth.config.security.dto

data class LoginRequest(
    val loginId: String,
    val password: String,
)
