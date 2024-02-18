package com.neohoon.core.authentication.token

import com.neohoon.core.authentication.token.TokenValidateState.*
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*

@Component
class TokenProvider(
    @Value("\${neohoon.security.auth.jwt.secret}")
    secret: String,
    @Value("\${neohoon.security.auth.jwt.validity-in-seconds}")
    tokenValidityInSeconds: Long,
) {

    private val AUTHORITIES_NAME = "auth"
    private val VALIDATION_KEY_NAME = "vk"
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val tokenValidityInMilliSeconds: Long = tokenValidityInSeconds * 1000

    private val log = LoggerFactory.getLogger(this::class.java)

    fun createToken(loginId: String, authorities: Collection<String>, validationKey: String): String {

        val now = Date()

        return Jwts.builder()
            .subject(loginId)
            .claim(AUTHORITIES_NAME, authorities.joinToString(","))
            .claim(VALIDATION_KEY_NAME, validationKey)
            .issuedAt(now)
            .expiration(Date(now.time + tokenValidityInMilliSeconds))
            .signWith(key)
            .compact()
    }

    fun getAuthentication(token: String): TokenUserDto {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        val authorities = extractAuthorityFromClaims(claims)

        return TokenUserDto(
            claims.subject,
            extractAuthorityFromClaims(claims)
        )
    }

    fun accessTokenValidState(token: String): TokenValidateState {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            log.debug("valid token")
            VALID;
        } catch (e: ExpiredJwtException) {
            log.debug("expired token : {}", e.message)
            EXPIRED
        } catch (e: Exception) {
            log.debug("invalid token : {}", token, e)
            INVALID
        }
    }

    fun getUserOfExpiredToken(jwt: String): TokenUserDto {
        val claims = try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
        return TokenUserDto(
            claims.subject,
            extractAuthorityFromClaims(claims),
            claims.get(VALIDATION_KEY_NAME, String::class.java)
        )
    }

    fun extractAuthorityFromClaims(claims: Claims): MutableCollection<String> {
        return claims[AUTHORITIES_NAME].toString().split(",")
            .filter { StringUtils.hasText(it) }
            .toMutableList()
    }

}