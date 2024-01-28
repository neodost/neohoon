package com.neohoon.api.app.repository

import com.neohoon.domain.test.TestSubData
import org.springframework.data.jpa.repository.JpaRepository

interface TestSubDataRepository : JpaRepository<TestSubData, Long> {
}