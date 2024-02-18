package com.neohoon.auth.config.security.dto

data class LoginRequest(
    val username: String,
    val password: String,
)
