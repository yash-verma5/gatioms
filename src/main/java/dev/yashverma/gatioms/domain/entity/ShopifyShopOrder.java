// File: src/main/java/dev/yashverma/gatioms/domain/entity/ShopifyShopOrder.java
// OFBiz table: shopify_shop_order
// Depends on: ShopifyConfig.java, OrderHeader.java, ShopifyShopOrderId.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Deduplication bridge between Shopify and internal OMS.
 */
@Entity
@Table(name = "shopify_shop_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopifyShopOrder extends BaseEntity {

    @EmbeddedId
    private ShopifyShopOrderId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderHeader orderHeader;

    @Column(name = "shopify_order_name", length = 50)
    private String shopifyOrderName;

    @CreationTimestamp
    @Column(name = "import_date", updatable = false)
    private LocalDateTime importDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shopId")
    @JoinColumn(name = "shop_id", columnDefinition = "VARCHAR(20)")
    private ShopifyConfig shopifyConfig;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopifyShopOrder that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShopifyShopOrder{id=" + id + ", name='" + shopifyOrderName + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **Deduplication Logic** implemented at the schema level via a composite primary key 
 * on `shopify_order_id` + `shop_id`.
 *
 * ### 2. How it maps to enterprise equivalents?
 * Mirrors standard enterprise custom integration patterns. It ensures that no matter how 
 * many times the Shopify sync job runs, the same Shopify order is never imported 
 * twice.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Race Conditions**: If two instances of the sync job pull the same new order 
 * simultaneously, both might try to insert.
 * **How to spot**: `ConstraintViolationException` (Duplicate entry) in the second job.
 */
