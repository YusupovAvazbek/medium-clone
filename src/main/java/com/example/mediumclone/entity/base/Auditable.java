package com.example.mediumclone.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "is_blocked", columnDefinition = "BOOLEAN default false")
    private boolean blocked;

    @Column(name = "is_active", columnDefinition = "int default 1")
    private short active;
    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp default now()")
    private LocalDateTime updatedAt;
}
