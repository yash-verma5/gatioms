// File: src/main/java/dev/yashverma/gatioms/domain/entity/GoodIdentificationId.java
// OFBiz table: good_identification (PK part)
// Depends on: none

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for GoodIdentification.
 * @Data is safe on @Embeddable.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodIdentificationId implements Serializable {

    @Column(name = "good_identification_type_id", length = 20)
    private String goodIdentificationTypeId;

    @Column(name = "product_id", length = 20)
    private String productId;
}
