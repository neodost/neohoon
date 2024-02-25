package com.neohoon.auth.config.security.oauth

import com.neohoon.auth.app.repository.member.MemberOauthLoginRepository
import com.neohoon.auth.app.repository.member.MemberRepository
import com.neohoon.auth.app.repository.member.MemberRoleRepository
import com.neohoon.auth.config.security.oauth.attribute.KakaoAttribute
import com.neohoon.auth.config.security.oauth.attribute.NaverAttribute
import com.neohoon.auth.config.security.userdetails.UserInfo
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberOauthLogin
import com.neohoon.domain.entity.member.MemberAuthority
import com.neohoon.domain.enums.member.Provider
import com.neohoon.domain.enums.member.Authority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils

@Service
class CustomOauth2UserService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val memberOauthLoginRepository: MemberOauthLoginRepository,
) : DefaultOAuth2UserService() {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val user = super.loadUser(userRequest)

        val provider = Provider.valueOf(userRequest.clientRegistration.registrationId)

        val attribute = when (provider) {
            Provider.kakao -> KakaoAttribute(user.attributes)
            Provider.naver -> NaverAttribute(user.attributes)
        }

        val email = attribute.email

        if (!StringUtils.hasText(email)) {
            throw RuntimeException()
        }

        val login = memberOauthLoginRepository.findByProvider(attribute.provider, attribute.providerId)
            ?: let {
                val member = memberRepository.findByEmail(email) ?: memberRepository.save(Member(email)).also {
                    it.authorities.add(memberRoleRepository.save(
                        MemberAuthority(
                            it,
                            Authority.USER
                        )
                    ))
                }
                memberOauthLoginRepository.save(MemberOauthLogin(member, attribute.provider, attribute.providerId))
            }

        return UserInfo(
            username = login.username,
            authorities = login.member.authorities.map { SimpleGrantedAuthority(it.authority.name) }.toMutableList(),
            attribute = attribute
        )
    }

}