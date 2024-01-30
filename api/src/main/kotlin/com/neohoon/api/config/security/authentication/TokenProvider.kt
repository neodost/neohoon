package com.neohoon.api.config.security.authentication

import com.neohoon.api.config.security.authentication.TokenValidateState.*
import com.neohoon.api.config.security.userdetails.UserInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*

@Component
class TokenProvider(

    @Value("\${neohoon.auth.jwt.secret}")
    secret: String,

    @Value("\${neohoon.auth.jwt.validity-in-seconds}")
    tokenValidityInSeconds: Long,

    ) {

    private val AUTHORITIES_NAME = "auth"
    private val ID_NAME = "id"
    private val REFRESH_KEY_NAME = "lvrk"
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val tokenValidityInMilliSeconds: Long = tokenValidityInSeconds * 1000

    private val log = LoggerFactory.getLogger(this::class.java)


    fun createToken(user: UserInfo): String {

        val now = Date()

        return Jwts.builder()
            .subject(user.username)
            .claim(ID_NAME, user.id)
            .claim(AUTHORITIES_NAME, user.authorities.joinToString(",") { it.authority })
            .claim(REFRESH_KEY_NAME, user.key)
            .issuedAt(now)
            .expiration(Date(now.time + tokenValidityInMilliSeconds))
            .signWith(key)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        val authorities = extractAuthorityFromClaims(claims)

        val principal = UserInfo(
            id = claims.get(ID_NAME, Integer::class.java).toLong(),
            authorities = authorities
        )

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
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

    fun getUserOfExpiredToken(jwt: String): UserInfo {
        val claims = try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
        return UserInfo(
            claims.get(ID_NAME, Long::class.java),
            extractAuthorityFromClaims(claims),
            claims.get(REFRESH_KEY_NAME, String::class.java)
        )
    }

    fun extractAuthorityFromClaims(claims: Claims): MutableCollection<out GrantedAuthority> {
        return claims.get(AUTHORITIES_NAME).toString().split(",")
            .filter { StringUtils.hasText(it) }
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
    }

}