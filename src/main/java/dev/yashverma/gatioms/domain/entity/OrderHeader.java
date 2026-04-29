// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderHeader.java
// OFBiz table: order_header
// Depends on: Party.java, ProductStore.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The main container for an order.
 */
@Entity
@Table(name = "order_header")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHeader extends BaseEntity {

    @Id
    @Column(name = "order_id", length = 20)
    private String orderId;

    @Column(name = "order_type_id", length = 20)
    private String orderTypeId;

    @Column(name = "status_id", length = 20)
    private String statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_party_id")
    private Party customerParty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_store_id")
    private ProductStore productStore;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "grand_total", precision = 18, scale = 2)
    private BigDecimal grandTotal;

    @Builder.Default
    @Column(name = "currency_uom", length = 10)
    private String currencyUom = "USD";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHeader that)) return false;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "OrderHeader{id='" + orderId + "', status='" + statusId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **BigDecimal with precision and scale**. This ensures that financial totals 
 * are stored accurately without floating-point errors.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Direct mapping to `OrderHeader`. This is the single source of truth for the 
 * order's state and financial summary.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Concurrency**: If two threads try to update the same order status at the 
 * same time, you might have a "Lost Update".
 * **How to spot**: Use `@Version` (optimistic locking) to detect and prevent 
 * concurrent modification errors.
 */
