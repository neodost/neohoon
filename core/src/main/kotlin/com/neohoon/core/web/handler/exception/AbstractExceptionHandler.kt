package com.neohoon.core.web.handler.exception

import com.neohoon.core.message.MessageResolver
import com.neohoon.core.web.handler.exception.dto.ErrorResponse

abstract class AbstractExceptionHandler(
    private val messageResolver: MessageResolver
) {

    fun errorResponse(message: String, vararg args: Any) =
        ErrorResponse(messageResolver.getMessage(message, *args))

}