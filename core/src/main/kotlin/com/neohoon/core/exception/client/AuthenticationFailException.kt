package com.neohoon.core.exception.client

import com.neohoon.core.exception.BaseException
import org.springframework.http.HttpStatus

class AuthenticationFailException (
    override val message: String,
    override vararg val args: Any
) : BaseException(HttpStatus.UNAUTHORIZED, message, args) {
}