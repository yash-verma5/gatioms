// File: src/main/java/dev/yashverma/gatioms/domain/entity/ShopifyShopOrderId.java
// OFBiz table: shopify_shop_order (PK part)
// Depends on: none

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for ShopifyShopOrder.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyShopOrderId implements Serializable {

    @Column(name = "shopify_order_id", length = 50)
    private String shopifyOrderId;

    @Column(name = "shop_id", length = 20)
    private String shopId;
}
