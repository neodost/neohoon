package com.neohoon.api.exception

import org.springframework.http.HttpStatus

open class NeohoonBaseException(

    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val code: String? = null

): RuntimeException(code)