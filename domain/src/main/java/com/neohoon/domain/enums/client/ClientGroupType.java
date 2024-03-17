package com.neohoon.domain.enums.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientGroupType {
    SOLO("단독"),
    APARTMENT("아파트"),
    COMMUNITY("집단공급"),
    FACTORY("공장"),
    ;
    private final String typeName;
}
