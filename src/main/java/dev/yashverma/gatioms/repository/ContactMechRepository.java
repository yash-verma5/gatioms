package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.ContactMech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMechRepository extends JpaRepository<ContactMech, String> {
}
