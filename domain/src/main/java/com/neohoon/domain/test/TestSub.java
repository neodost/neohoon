package com.neohoon.domain.test;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "table_test_sub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_sub_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 30)
    private String subName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public TestSub(Test test, String subName) {
        this.subName = subName;
        this.test = test;
    }
}
