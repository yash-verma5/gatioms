// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderItemId.java
// OFBiz table: order_item (PK part)
// Depends on: none

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for OrderItem.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {

    @Column(name = "order_id", length = 20)
    private String orderId;

    @Column(name = "order_item_seq_id", length = 20)
    private String orderItemSeqId;
}
