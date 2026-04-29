package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.ShopifyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopifyConfigRepository extends JpaRepository<ShopifyConfig, String> {

    @Transactional(readOnly = true)
    List<ShopifyConfig> findByIsActive(String isActive);

    @Transactional(readOnly = true)
    Optional<ShopifyConfig> findByShopId(String shopId);
}
