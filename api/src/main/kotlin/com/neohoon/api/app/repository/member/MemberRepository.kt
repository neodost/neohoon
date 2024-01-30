package com.neohoon.api.app.repository.member

import com.neohoon.api.app.dto.member.MemberDto
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.enums.member.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsernameAndDeletedIsFalse(username: String): Member?

    @Query(
        """
        select m
        from Member m
            inner join m.memberOauth mo on mo.deleted = false
        where mo.provider = :provider
          and mo.providerId = :providerId
    """
    )
    fun findByMemberOauthInfo(@Param("provider") provider: Provider, @Param("providerId") providerId: String): Member?

    @Query(
        """
        select new com.neohoon.api.app.dto.member.MemberDto(m)
        from Member m
        where m.id = :id
          and m.deleted = false
    """
    )
    fun findMemberDtoById(@Param("id") id: Long): MemberDto?

}