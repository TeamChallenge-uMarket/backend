package com.example.securityumarket.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit extends CreatedAtAudit {
    @LastModifiedDate
    @Column(nullable = false, updatable = false, name = "last_update")
    private LocalDateTime lastUpdate;
}