// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderItem.java
// OFBiz table: order_item
// Depends on: OrderHeader.java, Product.java, OrderItemId.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single line item in an order.
 */
@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {

    @EmbeddedId
    private OrderItemId id;

    @Column(name = "order_item_type_id", length = 20)
    private String orderItemTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "quantity", precision = 18, scale = 6)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 18, scale = 3)
    private BigDecimal unitPrice;

    @Column(name = "status_id", length = 20)
    private String statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", columnDefinition = "VARCHAR(20)")
    private OrderHeader orderHeader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", product='" + (product != null ? product.getProductId() : "N/A") + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **EmbeddedId with ManyToOne Mapping**. We map the `orderId` part of the composite key 
 * back to the `OrderHeader` entity using `@MapsId`.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Identical to `OrderItem`. It stores a snapshot of the `itemDescription` and price 
 * to ensure that future product changes don't affect historical orders.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Product ID Nulls**: If the `product_id` is null (e.g. for a custom non-catalog item), 
 * the `@ManyToOne` mapping might cause issues if not handled.
 * **How to spot**: `TransientObjectException` if trying to save an item with a 
 * non-existent product.
 */
