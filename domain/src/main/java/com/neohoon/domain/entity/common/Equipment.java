package com.neohoon.domain.entity.common;

import com.neohoon.domain.entity.BaseEntity;
import com.neohoon.domain.enums.common.EquipmentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "equipment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Equipment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type", nullable = false)
    private EquipmentType type;

    @Column(name = "serial_number", nullable = false, length = 30)
    private String serial;

    @Column(name = "manufacture_date", nullable = true)
    private LocalDate manufactureDate;
}
