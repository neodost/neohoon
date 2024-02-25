package com.neohoon.core.security.userdetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

open class UserInfo(
    private val username: String,
    private val authorities: MutableCollection<out GrantedAuthority>,
    val validationKey: String = UUID.randomUUID().toString(),
) : UserDetails {

    override fun getAuthorities(): MutableSet<out GrantedAuthority> = authorities.toMutableSet()

    override fun getPassword(): String = ""

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun toString(): String {
        return "UserInfo(authorities=$authorities, username=$username, validationKey='$validationKey'"
    }

}