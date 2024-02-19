package com.neohoon.auth.app.redisrepository

import com.neohoon.auth.app.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
}