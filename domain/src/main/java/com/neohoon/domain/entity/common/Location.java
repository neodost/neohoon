package com.neohoon.domain.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    @Column(name = "latitude", nullable = false)
    private double lat;
    @Column(name = "longitude", nullable = false)
    private double lng;
}
