// File: src/main/java/dev/yashverma/gatioms/domain/entity/PostalAddress.java
// OFBiz table: postal_address
// Depends on: ContactMech.java, BaseEntity.java

package dev.yashverma.gatioms.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Represents a physical mailing address.
 * Shares primary key with ContactMech.
 */
@Entity
@Table(name = "postal_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostalAddress extends BaseEntity {

    @Id
    @Column(name = "contact_mech_id", length = 20)
    private String contactMechId;

    @Column(name = "to_name", length = 100)
    private String toName;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state_province_geo_id", length = 20)
    private String stateProvinceGeoId;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country_geo_id", length = 20)
    private String countryGeoId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "contact_mech_id")
    private ContactMech contactMech;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostalAddress that)) return false;
        return Objects.equals(contactMechId, that.contactMechId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactMechId);
    }

    @Override
    public String toString() {
        return "PostalAddress{id='" + contactMechId + "', city='" + city + "'}";
    }
}

/**
 * ## 🧠 Learning Checkpoint
 *
 * ### 1. What Spring Boot/JPA concept was used?
 * **@MapsId** for shared primary key with `ContactMech`.
 *
 * ### 2. How it maps to OFBiz/Moqui equivalent?
 * Mirrors the `PostalAddress` entity. OFBiz uses this for all shipping/billing addresses.
 *
 * ### 3. One thing that could go wrong and how to spot it?
 * **Truncation**: `postal_code` is length 20. Some international codes or extra formatting 
 * might exceed this.
 * **How to spot**: `DataIntegrityViolationException` on save.
 */
