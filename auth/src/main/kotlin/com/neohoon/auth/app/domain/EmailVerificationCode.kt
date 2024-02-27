package com.neohoon.auth.app.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import kotlin.random.Random

@RedisHash("neohoon:verification:join:email")
data class EmailVerificationCode(
    @Id
    val code: String = String.format("%06d", Random.nextInt(1_000_000)),
    @Indexed
    val username: String,
    val validationKey: String,
    @TimeToLive
    val expiration: Long
)