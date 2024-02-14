package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import com.neohoon.core.web.handler.exception.dto.ErrorResponseDetail
import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
class ValidationExceptionHandler(
    private val messageResolver: MessageResolver
) : AbstractExceptionHandler(messageResolver) {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationException(e: ConstraintViolationException) =
        errorResponse("errors.validation").apply {
            errors.addAll(e.constraintViolations.map {
                ErrorResponseDetail(
                    it.propertyPath.last().name,
                    it.message
                )
            })
        }


}