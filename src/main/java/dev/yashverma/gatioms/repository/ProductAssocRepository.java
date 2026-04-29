package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.ProductAssoc;
import dev.yashverma.gatioms.domain.entity.ProductAssocId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAssocRepository extends JpaRepository<ProductAssoc, ProductAssocId> {
}
