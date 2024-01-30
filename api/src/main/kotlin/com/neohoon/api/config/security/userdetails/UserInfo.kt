package com.neohoon.api.config.security.userdetails

import com.neohoon.api.config.security.oauth.attribute.OAuth2Attribute
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class UserInfo(
    id: Long,
//    username: String,
    authorities: MutableCollection<out GrantedAuthority>,
    key: String = UUID.randomUUID().toString().substring(0..7),
    attribute: OAuth2Attribute? = null,
) : UserDetails, OAuth2User {

    private val authorities: MutableCollection<out GrantedAuthority> = authorities

    val id: Long = id
//    val username: String = username
    val key: String = key

    private val attribute: OAuth2Attribute? = attribute

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getPassword(): String = ""

    override fun getUsername(): String = id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getName(): String? {
        return attribute?.providerId
    }

    override fun getAttributes(): MutableMap<String, Any>? {
        return attribute?.attributes
    }

    override fun toString(): String {
        return "UserInfo(authorities=$authorities, id=$id, key='$key', attribute=$attribute)"
    }

}