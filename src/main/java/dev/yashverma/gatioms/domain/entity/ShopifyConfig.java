// File: src/main/java/dev/yashverma/gatioms/domain/entity/ShopifyConfig.java
// OFBiz table: shopify_config
// Depends on: ProductStore.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Stores credentials and sync status for a Shopify shop.
 */
@Entity
@Table(name = "shopify_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopifyConfig extends BaseEntity {

    @Id
    @Column(name = "shop_id", length = 20)
    private String shopId;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shopify_url")
    private String shopifyUrl;

    @Column(name = "access_token")
    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_store_id")
    private ProductStore productStore;

    @Column(name = "last_product_sync_date")
    private LocalDateTime lastProductSyncDate;

    @Column(name = "last_order_sync_date")
    private LocalDateTime lastOrderSyncDate;

    @Builder.Default
    @Column(name = "is_active", length = 1)
    private String isActive = "Y";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopifyConfig that)) return false;
        return Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId);
    }

    @Override
    public String toString() {
        return "ShopifyConfig{id='" + shopId + "', url='" + shopifyUrl + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **@Builder.Default** to ensure that new instances created via the builder pattern 
 * still respect the default value of 'Y' for `is_active`.
 *
 * ### 2. How it maps to enterprise equivalents?
 * This is a **Gati OMS Custom** entity. It centralizes the integration parameters 
 * that would typically be scattered across `SystemProperty` or `WebSite` settings.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Security Leak**: Accidentally returning `accessToken` in a public REST API.
 * **How to spot**: Use `@JsonIgnore` or dedicated DTOs to prevent credential exposure.
 */
