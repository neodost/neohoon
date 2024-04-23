package com.neohoon.domain.entity.member;

import com.neohoon.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true, length = 320)
    private String email;

    @Embedded
    private MemberName name;

    private boolean verifyEmail = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private final List<MemberAuthority> authorities = new ArrayList<>();

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, MemberName name) {
        this(email);
        this.name = name;
    }

    public void markEmailVerified() {
        this.verifyEmail = true;
    }
}
