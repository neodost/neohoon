package com.neohoon.auth.exception.security

import com.neohoon.core.exception.BaseException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException

class PasswordNotMatchesException : BadCredentialsException (
    "Password not match your password",
)