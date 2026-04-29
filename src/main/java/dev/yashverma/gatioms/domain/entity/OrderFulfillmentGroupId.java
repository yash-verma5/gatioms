// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderFulfillmentGroupId.java
// OFBiz table: order_item_ship_group (PK part)
// Depends on: none

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for OrderFulfillmentGroup.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFulfillmentGroupId implements Serializable {

    @Column(name = "order_id", length = 20)
    private String orderId;

    @Column(name = "ship_group_seq_id", length = 20)
    private String shipGroupSeqId;
}
