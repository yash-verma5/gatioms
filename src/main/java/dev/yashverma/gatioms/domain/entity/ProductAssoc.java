// File: src/main/java/dev/yashverma/gatioms/domain/entity/ProductAssoc.java
// OFBiz table: product_assoc
// Depends on: Product.java, ProductAssocType.java, ProductAssocId.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Defines relationships between products (e.g. Virtual parent to Variant child).
 */
@Entity
@Table(name = "product_assoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAssoc extends BaseEntity {

    @EmbeddedId
    private ProductAssocId id;

    @Column(name = "thru_date")
    private LocalDateTime thruDate;

    @Column(name = "sequence_num")
    private Integer sequenceNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", columnDefinition = "VARCHAR(20)")
    private Product virtualProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productIdTo")
    @JoinColumn(name = "product_id_to", columnDefinition = "VARCHAR(20)")
    private Product variantProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productAssocTypeId")
    @JoinColumn(name = "product_assoc_type_id", columnDefinition = "VARCHAR(20)")
    private ProductAssocType productAssocType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAssoc that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductAssoc{id=" + id + "}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **Self-Referencing Relationships** via a link table. Both `virtualProduct` and 
 * `variantProduct` point to the same `Product` entity.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Identical to `ProductAssoc`. This is how you find all colors/sizes for a single 
 * "Master" product.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Circular References**: If you accidentally associate Product A as a variant 
 * of Product B, and Product B as a variant of Product A, you can create infinite 
 * recursion during JSON serialization.
 * **How to spot**: `StackOverflowError` when fetching product trees.
 */
