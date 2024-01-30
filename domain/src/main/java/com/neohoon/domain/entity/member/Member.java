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

    @Column(nullable = false, updatable = false, unique = true, length = 512)
    private String username;

    @Column(nullable = false, updatable = false, unique = true, length = 320)
    private String email;

    @Column(length = 30)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private final List<MemberRole> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<MemberOauth> memberOauth = new ArrayList<>();

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void addOauth(MemberOauth oauth) {
        this.memberOauth.add(oauth);
    }
}
