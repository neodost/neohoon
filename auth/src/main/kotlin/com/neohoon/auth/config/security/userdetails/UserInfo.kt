package com.neohoon.auth.config.security.userdetails

import com.neohoon.auth.config.security.oauth.attribute.OAuth2Attribute
import com.neohoon.core.security.userdetails.UserInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class UserInfo(
    username: String,
    authorities: MutableCollection<out GrantedAuthority>,
    validationKey: String = UUID.randomUUID().toString(),
    private val attribute: OAuth2Attribute? = null,
) : UserInfo(username, authorities, validationKey), OAuth2User {

    override fun getName(): String? {
        return attribute?.providerId
    }

    override fun getAttributes(): MutableMap<String, Any>? {
        return this.attribute?.attributes
    }

}