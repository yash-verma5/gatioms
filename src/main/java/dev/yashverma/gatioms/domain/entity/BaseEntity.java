// File: src/main/java/dev/yashverma/gatioms/domain/entity/BaseEntity.java
// OFBiz equivalent: GenericEntity base audit fields

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base class for all entities to provide common audit fields.
 * Mirrors the GenericEntity audit pattern in OFBiz.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_stamp", updatable = false)
    private LocalDateTime createdStamp;

    @LastModifiedDate
    @Column(name = "last_updated_stamp")
    private LocalDateTime lastUpdatedStamp;

    @CreatedBy
    @Column(name = "created_by_user_login", updatable = false, length = 50)
    private String createdByUserLogin;

    @LastModifiedBy
    @Column(name = "last_modified_by_user_login", length = 50)
    private String lastModifiedByUserLogin;
}
