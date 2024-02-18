package com.neohoon.auth.config.security.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)