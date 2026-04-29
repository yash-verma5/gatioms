// File: src/main/java/dev/yashverma/gatioms/domain/entity/ProductAssocType.java
// OFBiz table: product_assoc_type
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Defines the type of association between products (e.g., PRODUCT_VARIANT, COMPONENT).
 */
@Entity
@Table(name = "product_assoc_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAssocType extends BaseEntity {

    @Id
    @Column(name = "product_assoc_type_id", length = 20)
    private String productAssocTypeId;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAssocType that)) return false;
        return Objects.equals(productAssocTypeId, that.productAssocTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productAssocTypeId);
    }

    @Override
    public String toString() {
        return "ProductAssocType{id='" + productAssocTypeId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * We used **String-based Identifiers** for primary keys. In modern Spring apps, people
 * often use `@GeneratedValue(strategy = GenerationType.IDENTITY)` on Longs. By using 
 * String IDs, we are opting for **natural/business keys** that are assigned before saving.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This maps to `ProductAssocType`. In OFBiz, this is critical for the "Virtual/Variant" 
 * relationship. You use the type `PRODUCT_VARIANT` to link a Parent Product to its SKUs.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Missing Seed Data**: If you try to save a `ProductAssoc` with a type ID that doesn't
 * exist in this table, Hibernate will throw a `ConstraintViolationException` (foreign key error).
 * **How to spot**: Look for "Referential integrity constraint violation" in your stack trace.
 */
