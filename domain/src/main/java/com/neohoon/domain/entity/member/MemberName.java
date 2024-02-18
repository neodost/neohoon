package com.neohoon.domain.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Embeddable
@Getter
public class MemberName {

    @Column(length = 30)
    private String firstName;
    @Column(length = 30)
    private String lastName;

    public String get() {
        return lastName + firstName;
    }

}
