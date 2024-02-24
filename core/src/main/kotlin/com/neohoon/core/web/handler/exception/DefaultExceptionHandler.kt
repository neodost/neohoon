package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

private val log = KotlinLogging.logger {}

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE + 3000)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class DefaultExceptionHandler(
    private val messageResolver: MessageResolver
) : AbstractExceptionHandler(messageResolver) {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) = errorResponse("errors.default").also {
        log.error(e) { "unhandled exception: $e" }
    }

}