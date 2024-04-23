package com.neohoon.domain.entity.contract;

import com.neohoon.domain.entity.BaseEntity;
import com.neohoon.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill_member_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BillMemberInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_member_info_id", nullable = false, updatable = false)
    private Long id;

    private String name;

    private String billType;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
