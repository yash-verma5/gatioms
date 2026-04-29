// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderStatusLog.java
// OFBiz table: order_status
// Depends on: OrderHeader.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Audit log for all order status changes.
 */
@Entity
@Table(name = "order_status_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderHeader orderHeader;

    @Column(name = "status_id", nullable = false, length = 20)
    private String statusId;

    @CreationTimestamp
    @Column(name = "status_datetime", updatable = false)
    private LocalDateTime statusDatetime;

    @Column(name = "changed_by_user_id", length = 50)
    private String changedByUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatusLog that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderStatusLog{id=" + id + ", status='" + statusId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **@GeneratedValue(strategy = GenerationType.IDENTITY)**. For audit logs, we finally 
 * use a standard auto-incrementing ID because there is no business natural key for 
 * an entry in a log.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Mirrors the `OrderStatus` entity in OFBiz. Every time `OrderHeader.statusId` changes, 
 * a new row is appended here.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Large Table Growth**: This table will grow significantly faster than `OrderHeader`. 
 * Millions of status changes will eventually slow down history lookups.
 * **How to spot**: Slow page loads when viewing "Order History" in the UI.
 */
