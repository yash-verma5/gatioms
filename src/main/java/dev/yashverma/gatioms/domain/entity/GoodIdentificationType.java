// File: src/main/java/dev/yashverma/gatioms/domain/entity/GoodIdentificationType.java
// OFBiz table: good_identification_type
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Defines the type of identification for a product (e.g., SKU, UPC, SHOPIFY_VAR_ID).
 * This is primarily used for reference and seeding data.
 */
@Entity
@Table(name = "good_identification_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodIdentificationType extends BaseEntity {

    @Id
    @Column(name = "good_identification_type_id", length = 20)
    private String goodIdentificationTypeId;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodIdentificationType that)) return false;
        return Objects.equals(goodIdentificationTypeId, that.goodIdentificationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodIdentificationTypeId);
    }

    @Override
    public String toString() {
        return "GoodIdentificationType{id='" + goodIdentificationTypeId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * We used the **@Entity** and **@Table** annotations to map a Java class to a database table.
 * The **@Id** annotation specifies the Primary Key. We used a String PK to mirror the
 * legacy OFBiz "ID" pattern rather than an auto-incremented Long.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This is a direct mapping of the `GoodIdentificationType` entity. In OFBiz, this is used
 * as "Reference Data" to categorize different types of IDs (like 'SKU', 'MANUFACTURER_ID').
 * In GatiOMS, we'll use this to tag Shopify-specific IDs.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Length Mismatch**: We defined the PK length as `varchar(20)`. If Shopify or another
 * system sends a Type ID longer than 20 chars (e.g. "SHOPIFY_VARIANT_IDENTIFIER"),
 * Hibernate will throw a `DataException` (Value too long).
 * **How to spot**: Check for SQL Error `22001` in your console.
 */
