package com.neohoon.api.config.security.oauth

import com.neohoon.api.app.repository.member.MemberOauthRepository
import com.neohoon.api.app.repository.member.MemberRepository
import com.neohoon.api.app.repository.member.MemberRoleRepository
import com.neohoon.api.config.security.oauth.attribute.KakaoAttribute
import com.neohoon.api.config.security.oauth.attribute.NaverAttribute
import com.neohoon.api.config.security.userdetails.UserInfo
import com.neohoon.domain.entity.member.Member
import com.neohoon.domain.entity.member.MemberOauth
import com.neohoon.domain.entity.member.MemberRole
import com.neohoon.domain.enums.member.Provider
import com.neohoon.domain.enums.member.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomOauth2UserService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val memberOauthRepository: MemberOauthRepository,
) : DefaultOAuth2UserService() {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val user = super.loadUser(userRequest)

        val provider = Provider.valueOf(userRequest.clientRegistration.registrationId)

        val attribute = when (provider) {
            Provider.kakao -> KakaoAttribute(user.attributes)
            Provider.naver -> NaverAttribute(user.attributes)
        }

        val member = memberRepository.findByMemberOauthInfo(attribute.provider, attribute.providerId)
            ?: let {
                val member = Member(
                    generateUsernameByProvider(attribute.providerId, provider),
                    attribute.email
                ).also { memberRepository.save(it) }
                val memberOauth =
                    MemberOauth(member, provider, attribute.providerId).also { memberOauthRepository.save(it) }
                member.addOauth(memberOauth)

                val memberRole = MemberRole(member, Role.USER).also { memberRoleRepository.save(it) }
                member.roles.add(memberRole)
                member
            }

        return UserInfo(
            id = member.id!!,
            authorities = member.roles.map { SimpleGrantedAuthority(it.role.name) }.toMutableList(),
            attribute = attribute
        )
    }

    private fun generateUsernameByProvider(providerId: String, provider: Provider): String =
        "${providerId}@${provider.name}"

}