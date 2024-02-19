package com.neohoon.auth.app.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash("neohoon:auth:refresh-token")
data class RefreshToken(
    @Id
    val token: String = UUID.randomUUID().toString(),
    @Indexed
    val username: String,
    val validationKey: String,
    @TimeToLive
    val expiration: Long
)