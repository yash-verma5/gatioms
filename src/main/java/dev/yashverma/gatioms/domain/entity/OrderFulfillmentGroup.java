// File: src/main/java/dev/yashverma/gatioms/domain/entity/OrderFulfillmentGroup.java
// OFBiz table: order_item_ship_group
// Depends on: OrderHeader.java, ContactMech.java, OrderFulfillmentGroupId.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Groups items for fulfillment (e.g. shipping together).
 */
@Entity
@Table(name = "order_item_ship_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFulfillmentGroup extends BaseEntity {

    @EmbeddedId
    private OrderFulfillmentGroupId id;

    @Column(name = "facility_id", length = 20)
    private String facilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_mech_id")
    private ContactMech contactMech;

    @Column(name = "shipment_method_type_id", length = 20)
    private String shipmentMethodTypeId;

    @Column(name = "fulfillment_type", length = 20)
    private String fulfillmentType;

    @Column(name = "status_id", length = 20)
    private String statusId;

    @Column(name = "carrier_party_id", length = 20)
    private String carrierPartyId;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "estimated_ship_date")
    private LocalDateTime estimatedShipDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", columnDefinition = "VARCHAR(20)")
    private OrderHeader orderHeader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderFulfillmentGroup that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderFulfillmentGroup{id=" + id + ", type='" + fulfillmentType + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **Audit fields** using Hibernate's `@CreationTimestamp` and `@UpdateTimestamp`.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This maps to `OrderItemShipGroup` (OFBiz) and `OrderPart` (Moqui). It's the 
 * logical place where the OMS decides "WHERE" and "HOW" items are fulfilled.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Missing Address**: If the `contact_mech_id` is null for a "SHIP_FROM_STORE" 
 * order, fulfillment will fail.
 * **How to spot**: NullPointerExceptions in your shipping label generation service.
 */
