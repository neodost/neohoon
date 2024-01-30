package com.neohoon.domain.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "member_role")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberToken {

    @Id
    @Column(nullable = false, updatable = false)
    private String token;

    @Column(nullable = false, updatable = false)
    private String validationKey;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    public MemberToken(Member member, String validationKey, int expireDay) {
        this.member = member;
        this.validationKey = validationKey;
        this.expireDate = LocalDateTime.now().plusDays(expireDay);
    }
}
