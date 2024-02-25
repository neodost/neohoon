package com.neohoon.core.security.authentication.token

class TokenUserDto(
    val username: String,
    val authorities: Collection<String>,
    val validationKey: String = ""
) {
}