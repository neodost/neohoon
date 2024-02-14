package com.neohoon.core.message

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class MessageResolver(
    private val messageSource: MessageSource
) {

    fun getMessage(key: String, vararg args: Any): String =
        messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale())
            ?: let {
                log.warn { "No message found for key $key" }
                key
            }

}