package com.neohoon.core.web.handler.exception.dto

class ErrorResponse(
    val message: String,
    val errors: MutableList<ErrorResponseDetail> = mutableListOf()
) {
}