package com.neohoon.domain.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_login")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_login_id", nullable = false, updatable = false)
    private Long id;

}
