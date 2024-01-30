package com.neohoon.api.config.security.dto

data class LoginRequest(
    val username: String,
    val password: String,
)
