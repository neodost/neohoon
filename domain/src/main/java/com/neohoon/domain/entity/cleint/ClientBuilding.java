package com.neohoon.domain.entity.cleint;

import com.neohoon.domain.entity.BaseCreationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="client_building")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClientBuilding extends BaseCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_building_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "client_name", nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_group_id", nullable = false)
    private ClientGroup group;
}
