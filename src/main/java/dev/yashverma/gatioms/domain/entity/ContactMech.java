// File: src/main/java/dev/yashverma/gatioms/domain/entity/ContactMech.java
// OFBiz table: contact_mech
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Represents a generic contact mechanism (Email, Address, Phone).
 */
@Entity
@Table(name = "contact_mech")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMech extends BaseEntity {

    @Id
    @Column(name = "contact_mech_id", length = 20)
    private String contactMechId;

    @Column(name = "contact_mech_type_id", length = 20)
    private String contactMechTypeId;

    @Column(name = "info_string")
    private String infoString;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactMech that)) return false;
        return Objects.equals(contactMechId, that.contactMechId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactMechId);
    }

    @Override
    public String toString() {
        return "ContactMech{id='" + contactMechId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * Basic **Table Mapping** with a String identifier.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This is the root of the `ContactMech` hierarchy. In OFBiz, `PostalAddress` and 
 * `TelecomNumber` extend this by sharing the same ID.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Ambiguous Type**: If `contact_mech_type_id` is 'POSTAL_ADDRESS' but the 
 * record only exists in `contact_mech` and not `postal_address`, joins will fail.
 * **How to spot**: `null` returns when querying for specific address types.
 */
