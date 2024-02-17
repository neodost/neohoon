package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import com.neohoon.core.web.handler.exception.dto.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE + 500)
class ServletExceptionHandler(
    private val messageResolver: MessageResolver
) : AbstractExceptionHandler(messageResolver) {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException) =
        ErrorResponse(e.localizedMessage)

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException) =
        ErrorResponse(e.localizedMessage)

}