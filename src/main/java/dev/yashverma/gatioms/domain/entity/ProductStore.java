// File: src/main/java/dev/yashverma/gatioms/domain/entity/ProductStore.java
// OFBiz table: product_store
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Represents a logical store where products are sold.
 */
@Entity
@Table(name = "product_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStore extends BaseEntity {

    @Id
    @Column(name = "product_store_id", length = 20)
    private String productStoreId;

    @Column(name = "store_name", length = 100)
    private String storeName;

    @Column(name = "company_name", length = 100)
    private String companyName;

    @Column(name = "default_currency_uom", length = 10)
    private String defaultCurrencyUom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStore that)) return false;
        return Objects.equals(productStoreId, that.productStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productStoreId);
    }

    @Override
    public String toString() {
        return "ProductStore{id='" + productStoreId + "', name='" + storeName + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * Standard **JPA Entity** mapping.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Mirrors `ProductStore`. This is the top-level container for all sales channel 
 * configurations (Shopify, Amazon, POS).
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Default Currency Nulls**: If an order is imported without a currency, and the 
 * `ProductStore` has no `default_currency_uom`, calculations might fail.
 * **How to spot**: NullPointerExceptions in your pricing/tax services.
 */
