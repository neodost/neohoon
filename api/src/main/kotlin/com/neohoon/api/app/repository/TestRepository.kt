package com.neohoon.api.app.repository

import com.neohoon.domain.test.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TestRepository : JpaRepository<Test, Long> {

    @Query(""" select t from Test t where t.id > 0 """)
    fun searchAll(pageable: Pageable): Page<Test>

}