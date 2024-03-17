package com.neohoon.domain.entity.cleint;

import com.neohoon.domain.entity.BaseCreationEntity;
import com.neohoon.domain.entity.common.Location;
import com.neohoon.domain.enums.client.ClientGroupType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClientGroup extends BaseCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_group_id")
    private Long id;

    @Column(name = "client_group_name", nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_group_type", nullable = false, length = 20)
    private ClientGroupType type;

    @Column(name = "zipcode", length = 6)
    private String zipcode;
    @Column(name = "client_group_address", nullable = false, length = 255)
    private String address;
    @Column(name = "region1", nullable = false, length = 30)
    private String region1;
    @Column(name = "region2", nullable = false, length = 30)
    private String region2;
    @Column(name = "region3", length = 30)
    private String region3;
    @Embedded
    private Location location;
}
