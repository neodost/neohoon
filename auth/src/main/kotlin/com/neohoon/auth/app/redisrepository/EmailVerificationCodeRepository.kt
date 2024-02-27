package com.neohoon.auth.app.redisrepository

import com.neohoon.auth.app.domain.EmailVerificationCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface EmailVerificationCodeRepository : CrudRepository<EmailVerificationCode, String> {
}