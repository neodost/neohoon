package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE + 3000)
class DefaultExceptionHandler(
    private val messageResolver: MessageResolver
) : AbstractExceptionHandler(messageResolver) {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) = errorResponse("errors.default")

}