package com.neohoon.api.app.repository

import com.neohoon.domain.test.TestSub
import org.springframework.data.jpa.repository.JpaRepository

interface TestSubRepository : JpaRepository<TestSub, Long> {
}