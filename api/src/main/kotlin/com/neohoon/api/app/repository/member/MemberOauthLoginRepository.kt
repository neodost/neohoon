package com.neohoon.api.app.repository.member

import com.neohoon.domain.entity.member.MemberOauthLogin
import com.neohoon.domain.enums.member.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberOauthLoginRepository: JpaRepository<MemberOauthLogin, Long> {

    @Query(
        """
        select mo
        from MemberOauthLogin mo
            inner join fetch mo.member m
        where mo.provider = :provider
          and mo.providerId = :providerId
          and mo.deleted = false
    """
    )
    fun findByProvider(@Param("provider") provider: Provider, @Param("providerId") providerId: String): MemberOauthLogin?

}