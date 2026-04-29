package dev.yashverma.gatioms.domain.enums;

import lombok.Getter;
import java.util.Set;
import java.util.Collections;

/**
 * OrderStatus defines the lifecycle of an order in GatiOMS.
 * Maps to StatusItem in OFBiz/Moqui.
 */
@Getter
public enum OrderStatus {
    ORDER_CREATED("ORDER_CREATED"),
    ORDER_APPROVED("ORDER_APPROVED"),
    ORDER_PROCESSING("ORDER_PROCESSING"),
    ORDER_COMPLETED("ORDER_COMPLETED"),
    ORDER_CANCELLED("ORDER_CANCELLED");

    private final String statusId;

    OrderStatus(String statusId) {
        this.statusId = statusId;
    }

    /**
     * Replaces OFBiz StatusValidChange table lookup.
     * Defines which transitions are legally allowed from the current state.
     */
    public Set<OrderStatus> getAllowedTransitions() {
        return switch (this) {
            case ORDER_CREATED -> Set.of(ORDER_APPROVED, ORDER_CANCELLED);
            case ORDER_APPROVED -> Set.of(ORDER_PROCESSING, ORDER_CANCELLED);
            case ORDER_PROCESSING -> Set.of(ORDER_COMPLETED, ORDER_CANCELLED);
            case ORDER_COMPLETED, ORDER_CANCELLED -> Collections.emptySet();
        };
    }

    public static OrderStatus fromStatusId(String statusId) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.statusId.equalsIgnoreCase(statusId)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown statusId: " + statusId);
    }

    @Override
    public String toString() {
        return statusId;
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/Java concept was used?
 * We used a **Type-safe Java Enum**. Enums in Java are full-blown classes that can have fields,
 * constructors, and even instance methods like `getAllowedTransitions()`.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * In OFBiz/Moqui, status logic is "Data Driven". You would query the `StatusValidChange` table
 * every time you want to see if a transition from `ORDER_CREATED` to `ORDER_APPROVED` is valid.
 * Here, we've "Hardcoded" the logic into the code. This is faster and prevents runtime DB errors
 * for core business logic, though it is less flexible than DB-driven status changes.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Mismatched IDs**: If you use "ORDER_CREATED" in your code but your legacy DB integration
 * expects "ORDER_CREATED_STATUS", the `fromStatusId` method will throw an `IllegalArgumentException`.
 * **How to spot**: Look for "Unknown statusId" errors in your logs during Shopify order import.
 */
