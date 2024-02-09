package com.neohoon.api.app.controller

import org.jasypt.encryption.StringEncryptor
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/\${neohoon.api.version}/test")
@Profile("local")
class TestController(
    private val jasyptStringEncryptor: StringEncryptor
) {
    @GetMapping("/encrypt/{text}")
    fun encrypt(@PathVariable text: String): String = jasyptStringEncryptor.encrypt(text)

    @GetMapping("/decrypt/{text}")
    fun decrypt(@PathVariable text: String): String = jasyptStringEncryptor.decrypt(text)

}