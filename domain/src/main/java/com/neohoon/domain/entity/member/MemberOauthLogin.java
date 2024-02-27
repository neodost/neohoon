package com.neohoon.domain.entity.member;

import com.neohoon.domain.enums.member.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("OAUTH")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberOauthLogin extends MemberLogin {

    @Enumerated(EnumType.STRING)
    @Column(length = 10, updatable = false)
    private Provider provider;

    @Column(updatable = false)
    private String providerId;

    public MemberOauthLogin(Member member, Provider provider, String providerId) {
        super(member);
        this.provider = provider;
        this.providerId = providerId;
    }
}
