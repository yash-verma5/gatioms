// File: src/main/java/dev/yashverma/gatioms/domain/entity/GoodIdentification.java
// OFBiz table: good_identification
// Depends on: GoodIdentificationType.java, Product.java, GoodIdentificationId.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Maps external IDs (SKU, Shopify ID) to internal products.
 */
@Entity
@Table(name = "good_identification", indexes = {
    @Index(name = "idx_good_id_value", columnList = "id_value, good_identification_type_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodIdentification extends BaseEntity {

    @EmbeddedId
    private GoodIdentificationId id;

    @Column(name = "id_value", nullable = false)
    private String idValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("goodIdentificationTypeId")
    @JoinColumn(name = "good_identification_type_id", columnDefinition = "VARCHAR(20)")
    private GoodIdentificationType goodIdentificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", columnDefinition = "VARCHAR(20)")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodIdentification that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GoodIdentification{id=" + id + ", value='" + idValue + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **@EmbeddedId** and **@MapsId**. This allows us to have a composite primary key 
 * where parts of the key are also foreign keys to other entities.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Identical to `GoodIdentification`. This is the bridge that allows GatiOMS to know 
 * that Shopify Variant ID `12345` is actually Product `SKU-999`.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Non-Unique ID Values**: If you allow two products to have the same SKU in the 
 * `good_identification` table, lookup queries will return multiple results and 
 * cause `NonUniqueResultException`.
 * **How to spot**: Shopify sync failing with "query did not return a unique result".
 */
