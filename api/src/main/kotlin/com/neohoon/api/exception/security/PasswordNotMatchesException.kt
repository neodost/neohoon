package com.neohoon.api.exception.security

import com.neohoon.api.exception.NeohoonBaseException
import org.springframework.http.HttpStatus

class PasswordNotMatchesException : NeohoonBaseException(
    HttpStatus.UNAUTHORIZED
)