package com.neohoon.api.app.controller

import com.neohoon.api.config.TestSearch
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jasypt.encryption.StringEncryptor
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.SortHandlerMethodArgumentResolver
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.support.HandlerMethodArgumentResolver

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/test")
@Validated
@Profile("local")
class TestController(
    private val jasyptStringEncryptor: StringEncryptor
) {
    @GetMapping("/encrypt")
    fun encrypt(@RequestBody text: String): String = jasyptStringEncryptor.encrypt(text)

    @GetMapping("/decrypt")
    fun decrypt(@RequestBody text: String): String = jasyptStringEncryptor.decrypt(text)

    @GetMapping("/pageable")
    fun pageable(
        @PageableDefault(page = 1, size = 22, sort = ["id,ASC", "name,DESC"], direction = Sort.Direction.ASC)
        pageable: Pageable
    ): String {
        SortHandlerMethodArgumentResolver().propertyDelimiter
        log.info { "pageable: ${pageable.javaClass}" }
        log.info { "pageable: $pageable" }
        log.info { "sort: ${pageable.sort}" }

        return "OK"
    }


}