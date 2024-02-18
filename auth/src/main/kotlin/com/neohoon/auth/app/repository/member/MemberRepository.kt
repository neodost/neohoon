package com.neohoon.auth.app.repository.member

import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.enums.member.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    @Query(
        """
        select m
        from Member m
            inner join MemberOauthLogin mo on m = mo.member and mo.deleted = false
        where mo.provider = :provider
          and mo.providerId = :providerId
    """
    )
    fun findByMemberOauthInfo(@Param("provider") provider: Provider, @Param("providerId") providerId: String): Member?

    @Query("""
        select m
        from Member m
        where m.email = :email
          and m.deleted = false
    """)
    fun findByEmail(@Param("email") email: String): Member?

}