package com.neohoon.api.config.security.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)