package com.neohoon.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@MappedSuperclass
@Getter
public class BaseEntity extends BaseCreationEntity {

    private Long deletedBy;
    private LocalDateTime deletedDate;
    @Column(nullable = false)
    private Boolean deleted = false;

    public void delete(Long loginUserId) {
        if (this.deleted) {
            return;
        }
        this.deleted = true;
        this.deletedDate = LocalDateTime.now();
        this.deletedBy = loginUserId;
    }

}
