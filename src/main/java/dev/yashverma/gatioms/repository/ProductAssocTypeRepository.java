package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.ProductAssocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAssocTypeRepository extends JpaRepository<ProductAssocType, String> {
}
