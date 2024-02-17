package com.neohoon.core.exception.client

import com.neohoon.core.exception.BaseException
import org.springframework.http.HttpStatus

open class ClientSideException(
    override val message: String,
    override vararg val args: Any
) : BaseException(HttpStatus.BAD_REQUEST, message, args) {
}