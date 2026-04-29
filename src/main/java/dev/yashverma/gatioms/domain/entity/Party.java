// File: src/main/java/dev/yashverma/gatioms/domain/entity/Party.java
// OFBiz table: party
// Depends on: BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * Represents any legal entity, including Persons and Organizations.
 * This is the base entity for the Party-Person and Party-Group hierarchy.
 */
@Entity
@Table(name = "party")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party extends BaseEntity {

    @Id
    @Column(name = "party_id", length = 20)
    private String partyId;

    @Column(name = "party_type_id", length = 20)
    private String partyTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Party party)) return false;
        return Objects.equals(partyId, party.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId);
    }

    @Override
    public String toString() {
        return "Party{id='" + partyId + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * We used **Hibernate Annotations** (@CreationTimestamp and @UpdateTimestamp). 
 * These automatically manage the `created_date` and `last_updated` timestamps without 
 * requiring manual setting in the service layer.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This is the exact equivalent of the `Party` entity. OFBiz uses a **"Table-per-Type"**
 * or **"Shared Primary Key"** inheritance pattern. `Party` is the parent, and `Person` 
 * or `PartyGroup` share the same `partyId`.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **TimeZone drift**: If the database server and the application server are in different 
 * timezones, `LocalDateTime` might show different times.
 * **How to spot**: Check if `created_date` in the DB matches your local `now()` during 
 * testing. Use `ZonedDateTime` or UTC configuration in `application.yml` to fix.
 */
