// File: src/main/java/dev/yashverma/gatioms/domain/entity/ProductAssocId.java
// OFBiz table: product_assoc (PK part)
// Depends on: none

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Composite key for ProductAssoc.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAssocId implements Serializable {

    @Column(name = "product_id", length = 20)
    private String productId;

    @Column(name = "product_id_to", length = 20)
    private String productIdTo;

    @Column(name = "product_assoc_type_id", length = 20)
    private String productAssocTypeId;

    @Column(name = "from_date")
    private LocalDateTime fromDate;
}
