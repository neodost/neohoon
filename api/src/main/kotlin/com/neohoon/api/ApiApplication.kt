package com.neohoon.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.neohoon"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
