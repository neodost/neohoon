package com.neohoon.domain.test;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_sub_data")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestSubData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_sub_data_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_sub_id")
    private TestSub testSub;

    public TestSubData(TestSub testSub, String data) {
        this.data = data;
        this.testSub = testSub;
    }
}
