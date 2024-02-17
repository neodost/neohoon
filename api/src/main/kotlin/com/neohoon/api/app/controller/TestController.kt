package com.neohoon.api.app.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jasypt.encryption.StringEncryptor
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}