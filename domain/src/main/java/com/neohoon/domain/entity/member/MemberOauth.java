package com.neohoon.domain.entity.member;

import com.neohoon.domain.entity.BaseEntity;
import com.neohoon.domain.enums.member.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_oauth")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberOauth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_oauth_id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, updatable = false)
    private Provider provider;

    @Column(nullable = false, updatable = false)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    public MemberOauth(Member member, Provider provider, String providerId) {
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
    }
}
