package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import com.neohoon.core.web.handler.exception.dto.ErrorResponse
import com.neohoon.core.web.handler.exception.dto.ErrorResponseDetail
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

private val log = KotlinLogging.logger {}

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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException) =
        errorResponse("errors.validation").apply {
            errors.addAll(e.bindingResult.globalErrors.map {
                errorResponseDetail(it)
            })
            errors.addAll(e.bindingResult.fieldErrors.map {
                ErrorResponseDetail(
                    it.field,
                    if (it.isBindingFailure) messageResolver.getMessage("errors.validation")
                    else it.defaultMessage
                )
            })
        }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ErrorResponse =
        errorResponse("errors.validation").also {
            log.warn(e) { "resolved validation exception : $e" }
        }

    fun errorResponseDetail(error: ObjectError): ErrorResponseDetail {
        return if (error.codes?.run { isNotEmpty() } == true) {
            ErrorResponseDetail(
                error.objectName,
                error.defaultMessage
            )
        } else {
            ErrorResponseDetail(
                error.objectName,
                messageResolver.getMessage("errors.validation")
            )
        }
    }
}