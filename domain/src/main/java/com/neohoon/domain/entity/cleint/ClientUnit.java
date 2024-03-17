package com.neohoon.domain.entity.cleint;

import com.neohoon.domain.entity.BaseCreationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="client_unit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClientUnit extends BaseCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_unit_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "client_unit_name", nullable = false, length = 30)
    private String unitName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_building_id", nullable = false)
    private ClientBuilding building;
}
