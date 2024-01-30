package com.neohoon.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Embeddable
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedBy
    private Long createdBy;
    @LastModifiedBy
    private Long lastModifiedBy;
    private Long deletedBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    private LocalDateTime deletedDate;
    @Column(nullable = false)
    private Boolean deleted = false;

    public void markDeleted(Long loginUserId) {
        if (this.deleted) {
            return;
        }
        this.deleted = true;
        this.deletedDate = LocalDateTime.now();
        this.deletedBy = loginUserId;
    }

}
