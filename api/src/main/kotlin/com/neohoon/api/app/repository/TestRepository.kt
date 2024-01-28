package com.neohoon.api.app.repository

import com.neohoon.domain.test.Test
import org.springframework.data.jpa.repository.JpaRepository

interface TestRepository : JpaRepository<Test, Long> {
}