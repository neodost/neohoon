package com.neohoon.domain.entity.oil;

import com.neohoon.domain.entity.BaseEntity;
import com.neohoon.domain.entity.cleint.ClientGroup;
import com.neohoon.domain.entity.common.Location;
import com.neohoon.domain.enums.oil.TankType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oil_tank")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tank extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tank_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tank_type", nullable = false, length = 10)
    private TankType type;

    @Column(name = "tank_name", nullable = false)
    private String name;

    @Embedded
    private Location location;

    @Column(name = "tank_volume", nullable = false)
    private int volume;
    @Column(name = "tank_gauge", nullable = false)
    private int gauge;
    @Column(name = "tank_max_gauge", nullable = false)
    private int maxGauge;
    @Column(name = "tank_min_gauge", nullable = false)
    private int minGauge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_group_id", nullable = false)
    private ClientGroup clientGroup;
}
