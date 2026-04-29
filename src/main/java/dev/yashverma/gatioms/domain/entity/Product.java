// File: src/main/java/dev/yashverma/gatioms/domain/entity/Product.java
// OFBiz table: product
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Represents a sellable product or a grouping of products (Virtual).
 */
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id", length = 20)
    private String productId;

    @Column(name = "product_type_id", length = 20)
    private String productTypeId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "is_virtual", length = 1)
    private String isVirtual = "N";

    @Builder.Default
    @Column(name = "is_variant", length = 1)
    private String isVariant = "N";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{id='" + productId + "', name='" + productName + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **columnDefinition = "TEXT"** to ensure the description field can hold large 
 * amounts of data, mapping to a CLOB/TEXT type in the DB.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Mirrors the `Product` entity. We preserve the `isVirtual` / `isVariant` flag 
 * logic which is the core of OFBiz's product data modeling.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Search Performance**: Querying by `product_name` without an index will slow 
 * down as the catalog grows.
 * **How to spot**: Slow API responses when searching products in the UI.
 */
