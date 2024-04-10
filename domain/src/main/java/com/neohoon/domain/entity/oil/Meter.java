package com.neohoon.domain.entity.oil;

import com.neohoon.domain.entity.cleint.ClientUnit;
import com.neohoon.domain.entity.common.Equipment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="oil_meter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, updatable = false)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tank_id", nullable = false)
    private Tank tank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_unit_id", nullable = false)
    private ClientUnit clientUnit;
}
