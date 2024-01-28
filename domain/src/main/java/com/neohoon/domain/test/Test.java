package com.neohoon.domain.test;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "table_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 12)
    private String name;

    public Test(String name) {
        this.name = name;
    }
}
