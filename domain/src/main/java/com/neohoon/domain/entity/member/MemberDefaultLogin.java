package com.neohoon.domain.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("DEFAULT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDefaultLogin extends MemberLogin {

    @Column(updatable = false, length = 320)
    private String loginId;
    private String password;

}
