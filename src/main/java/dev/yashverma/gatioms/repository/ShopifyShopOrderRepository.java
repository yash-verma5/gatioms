package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.ShopifyShopOrder;
import dev.yashverma.gatioms.domain.entity.ShopifyShopOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopifyShopOrderRepository extends JpaRepository<ShopifyShopOrder, ShopifyShopOrderId> {

    @Transactional(readOnly = true)
    @Query("SELECT s FROM ShopifyShopOrder s WHERE s.shopifyConfig.shopId = :shopId")
    List<ShopifyShopOrder> findByShopId(@Param("shopId") String shopId);

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(s) > 0 FROM ShopifyShopOrder s WHERE s.id.shopifyOrderId = :shopifyOrderId AND s.id.shopId = :shopId")
    boolean existsByShopifyOrderIdAndShopId(@Param("shopifyOrderId") String shopifyOrderId, @Param("shopId") String shopId);

    @Transactional(readOnly = true)
    @Query("SELECT s FROM ShopifyShopOrder s WHERE s.shopifyConfig.shopId = :shopId ORDER BY s.importDate DESC LIMIT 1")
    Optional<ShopifyShopOrder> findTopByShopIdOrderByImportDateDesc(@Param("shopId") String shopId);
}
