package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, String> {
}
