package com.neohoon.api.app.controller

import com.neohoon.api.app.dto.TestDto
import com.neohoon.api.app.repository.TestRepository
import com.neohoon.domain.test.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testRepository: TestRepository
) {

    @GetMapping
    fun hello(pageable: Pageable): Page<TestDto> = testRepository.searchAll(pageable)

}