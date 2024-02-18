package com.neohoon.domain.entity.member;

import com.neohoon.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "member_login")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberLogin extends BaseEntity {

    @Id
    @Column(name = "member_login_id", nullable = false, updatable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    public MemberLogin(Member member) {
        this.username = UUID.randomUUID().toString();
        this.member = member;
    }
}
