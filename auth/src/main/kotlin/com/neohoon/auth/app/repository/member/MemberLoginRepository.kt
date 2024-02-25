package com.neohoon.auth.app.repository.member

import com.neohoon.domain.entity.member.MemberLogin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberLoginRepository : JpaRepository<MemberLogin, String> {

    @Query(
        """
        select l
        from MemberLogin l
            inner join fetch l.member m
            left outer join fetch m.authorities r
        where l.username = :username
    """
    )
    fun findWithAuthoritiesByUsername(@Param("username") username: String): MemberLogin?

}