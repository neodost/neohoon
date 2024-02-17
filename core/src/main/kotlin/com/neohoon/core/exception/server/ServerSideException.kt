package com.neohoon.core.exception.server

import com.neohoon.core.exception.BaseException
import org.springframework.http.HttpStatus

open class ServerSideException(
    override val message: String,
    override vararg val args: Any
) : BaseException(HttpStatus.INTERNAL_SERVER_ERROR, message, args) {
}