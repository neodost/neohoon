package com.neohoon.api.app.repository

import com.neohoon.api.app.dto.TestDto
import com.neohoon.domain.test.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TestRepository : JpaRepository<Test, Long> {

    @Query("""
        select new com.neohoon.api.app.dto.TestDto(t.id, t.name, ts.id, ts.subName, tsd.id, tsd.data)
        from Test t
            left outer join TestSub ts on t = ts.test
            left outer join TestSubData tsd on ts = tsd.testSub
        where t.id > 0
    """)
    fun searchAll(pageable: Pageable): Page<TestDto>

}