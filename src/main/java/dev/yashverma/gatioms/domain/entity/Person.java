// File: src/main/java/dev/yashverma/gatioms/domain/entity/Person.java
// OFBiz table: person
// Depends on: Party.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents natural persons in the system.
 * Shares the primary key (party_id) with the Party entity.
 */
@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends BaseEntity implements org.springframework.data.domain.Persistable<String> {

    @Id
    @Column(name = "party_id", length = 20)
    private String partyId;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "party_id")
    private Party party;

    @Override
    public String getId() {
        return partyId;
    }

    @Override
    public boolean isNew() {
        return getCreatedStamp() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(partyId, person.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId);
    }

    @Override
    public String toString() {
        return "Person{id='" + partyId + "', name='" + firstName + " " + lastName + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * We used **@MapsId**. This is a powerful JPA annotation that tells Hibernate that 
 * the Primary Key of the `Person` entity is actually derived from the `Party` entity.
 * It ensures that a `Person` cannot exist without a corresponding `Party` record.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * This implements the **"Table-per-Type"** inheritance. In OFBiz, if you want a person's name,
 * you join `Party` and `Person` on `partyId`. Here, the `@OneToOne` mapping handles 
 * that relationship cleanly.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **LazyInitializationException**: Since the `Party` relationship is marked as `FetchType.LAZY`, 
 * if you try to access `person.getParty()` outside of a `@Transactional` session, it will crash.
 * **How to spot**: Look for "could not initialize proxy - no Session" in your logs.
 */
