package com.neohoon.api.config

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class TestSearch(
    pageable: Pageable
) : PageRequest(pageable.pageNumber, pageable.pageSize, pageable.sort) {
}