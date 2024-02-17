package com.neohoon.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class BaseException(
    private val status: HttpStatus,
    override val message: String,
    open vararg val args: Any
) : ResponseStatusException(status) {
}