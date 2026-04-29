package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.GoodIdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodIdentificationTypeRepository extends JpaRepository<GoodIdentificationType, String> {
}
