package com.neohoon.auth.exception.security

import com.neohoon.core.exception.BaseException
import org.springframework.http.HttpStatus

class PasswordNotMatchesException : BaseException(
    HttpStatus.UNAUTHORIZED,
    ""
)